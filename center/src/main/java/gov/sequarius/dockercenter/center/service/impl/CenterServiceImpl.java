package gov.sequarius.dockercenter.center.service.impl;

import gov.sequarius.dockercenter.center.common.Constant;
import gov.sequarius.dockercenter.center.service.CenterService;
import gov.sequarius.dockercenter.center.thrift.sever.DCServerEventHandler;
import gov.sequarius.dockercenter.common.domain.CommonResult;
import gov.sequarius.dockercenter.common.rpc.NodeInfoDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Sequarius on 2017/3/17.
 */
@Service
@Slf4j
public class CenterServiceImpl implements CenterService {
    @Resource
    RedisTemplate<String, Map<String, NodeInfoDTO>> redisTemplate;

    Map<String, NodeInfoDTO> ipNodeTable;

    Map<Integer, NodeInfoDTO> tagNodeTable;

    @Value("${gov.sequarius.docker.center.max-node-count}")
    private Integer MAX_NODE_COUNT;

    @Resource
    DCServerEventHandler dcServerEventHandler;

    @PostConstruct
    private void init() {
        ipNodeTable = redisTemplate.opsForValue().get(Constant.KEY_NODE_INFO);
        if (ipNodeTable == null) {
            ipNodeTable = new HashMap<>();
            persistentNodeInfo();
        }
        if(tagNodeTable ==null){
            tagNodeTable =new HashMap<>();
        }
        ipNodeTable.forEach((ip, nodeInfo) -> tagNodeTable.put(nodeInfo.getTag(), nodeInfo));
    }

    private void persistentNodeInfo() {
        redisTemplate.opsForValue().set(Constant.KEY_NODE_INFO, ipNodeTable);
    }


    @Override
    public CommonResult registerNodeInfo(NodeInfoDTO nodeInfoDTO) {

        synchronized (this) {
            String nodeIp=dcServerEventHandler.getSocketByThradId(Thread.currentThread().getId()).getInetAddress().getHostAddress();
            nodeInfoDTO.setIp(nodeIp);
            if (getCurrentNodeCount() >= MAX_NODE_COUNT) {
                return CommonResult.result(false, String.format("node count is over the max limit count of %s",
                        MAX_NODE_COUNT));
            }
            if (ipNodeTable.containsKey(nodeInfoDTO.getIp())) {
                return CommonResult.result(false, "node is already registered.");
            }
            nodeInfoDTO.setTag(getCurrentNodeCount()+1);
            ipNodeTable.put(nodeIp, nodeInfoDTO);
            tagNodeTable.put(nodeInfoDTO.getTag(),nodeInfoDTO);
        }
        persistentNodeInfo();
        return CommonResult.result(true, String.format("node %s register successfully.", nodeInfoDTO.getTag()));
    }


    @Override
    public CommonResult removeNodeInfoByTag() {
        String ip=dcServerEventHandler.getSocketByThradId(Thread.currentThread().getId()).getInetAddress()
                .getHostAddress();
        NodeInfoDTO nodeInfo=ipNodeTable.get(ip);
        if (nodeInfo==null) {
            return CommonResult.result(false, String.format("cant find node %s.", ip));
        }
        tagNodeTable.remove(nodeInfo.getTag());
        ipNodeTable.remove(ip);
        persistentNodeInfo();
        return CommonResult.result(true, String.format("node %s remove successfully.", ip));
    }

    @Override
    public Map<String, NodeInfoDTO> getNodeInfoMap() {
        return ipNodeTable;
    }

    private int getCurrentNodeCount(){
        return getNodeInfoMap().size();
    }

    @Override
    public NodeInfoDTO findNodeByTag(Integer tag) {
        NodeInfoDTO nodeInfoDTO = tagNodeTable.get(tag);
        return nodeInfoDTO;
    }

    @Override
    public CommonResult updateNodeInfo(NodeInfoDTO nodeInfoDTO) {
        String nodeIp=dcServerEventHandler.getSocketByThradId(Thread.currentThread().getId()).getInetAddress().getHostAddress();
        NodeInfoDTO nodeInfo = ipNodeTable.get(nodeIp);
        nodeInfoDTO.setIp(nodeIp);
        nodeInfoDTO.setResponseTime(System.currentTimeMillis()-nodeInfo.getCallTime());
        if(nodeInfo==null){
            return CommonResult.result(false,"cant update node info because node hadn't register yet, try call " +
                    "register node at first");
        }
        BeanUtils.copyProperties(nodeInfoDTO,nodeInfo);
        persistentNodeInfo();
        return CommonResult.result(true,"synchronized node info successfully!");
    }

}
