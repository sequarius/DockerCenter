package gov.sequarius.dockercenter.center.service.impl;

import gov.sequarius.dockercenter.center.common.Constant;
import gov.sequarius.dockercenter.center.service.CenterService;
import gov.sequarius.dockercenter.common.domain.CommonResult;
import gov.sequarius.dockercenter.common.rpc.NodeInfoDTO;
import gov.sequarius.dockercenter.common.util.IPUtil;
import lombok.extern.slf4j.Slf4j;
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
        String nodeIp = nodeInfoDTO.getIp();
        if (!IPUtil.isLegalIP(nodeIp)) {
            return CommonResult.result(false, String.format("ip %s is an illegal address", nodeIp));
        }

        synchronized (this) {
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
        return CommonResult.result(true, String.format("node %s register successfully.", nodeIp));
    }


    @Override
    public CommonResult removeNodeInfoByIp(String ip) {
        if (!ipNodeTable.containsKey(ip)) {
            return CommonResult.result(false, String.format("cant find node %s.", ip));
        }
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

}
