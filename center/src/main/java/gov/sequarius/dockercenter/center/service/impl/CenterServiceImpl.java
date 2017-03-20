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

    Map<String, NodeInfoDTO> nodeInfo;

    @Value("${gov.sequarius.docker.center.max-node-count}")
    private Integer MAX_NODE_COUNT;

    @PostConstruct
    private void init() {
        nodeInfo = redisTemplate.opsForValue().get(Constant.KEY_NODE_INFO);
        if (nodeInfo == null) {
            nodeInfo = new HashMap<>();
            persistentNodeInfo();
        }
    }

    private void persistentNodeInfo() {
        redisTemplate.opsForValue().set(Constant.KEY_NODE_INFO, nodeInfo);
    }


    @Override
    public CommonResult registerNodeInfo(NodeInfoDTO nodeInfoDTO) {
        if (nodeInfo.containsKey(nodeInfoDTO.getIp())) {
            return CommonResult.result(false, "node is already registered.");
        }
        if (nodeInfo.size() >= MAX_NODE_COUNT) {
            return CommonResult.result(false, String.format("node count is over the max limit count of %s",
                    MAX_NODE_COUNT));
        }
        String nodeIp = nodeInfoDTO.getIp();
        if (!IPUtil.isLegalIP(nodeIp)) {
            return CommonResult.result(false, String.format("ip %s is an illegal address", nodeIp));
        }
        nodeInfo.put(nodeIp, nodeInfoDTO);
        persistentNodeInfo();
        return CommonResult.result(true, String.format("node %s register successfully.", nodeIp));
    }


    @Override
    public CommonResult removeNodeInfoByIp(String ip) {
        if (!nodeInfo.containsKey(ip)) {
            return CommonResult.result(false, String.format("cant find node %s.",ip));
        }
        nodeInfo.remove(ip);
        persistentNodeInfo();
        return CommonResult.result(true,String.format("node %s remove successfully.",ip));
    }

    @Override
    public Map<String, NodeInfoDTO> getNodeInfoMap() {
        return nodeInfo;
    }

}
