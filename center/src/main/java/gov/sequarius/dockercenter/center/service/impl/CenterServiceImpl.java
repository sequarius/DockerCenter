package gov.sequarius.dockercenter.center.service.impl;

import gov.sequarius.dockercenter.center.service.CenterService;
import gov.sequarius.dockercenter.common.rpc.NodeInfoDTO;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Sequarius on 2017/3/17.
 */
@Service
public class CenterServiceImpl implements CenterService{
    @Resource
    RedisTemplate<String,NodeInfoDTO> redisTemplate;
    @Override
    public List<NodeInfoDTO> getNodeInfoDtos() {
        NodeInfoDTO node= new NodeInfoDTO();
        node.setName("xdsyihao");
        redisTemplate.opsForValue().set("xdsjsd",node);
        return null;
    }

    @Override
    public boolean registerNodeInfo(NodeInfoDTO nodeInfoDTO) {
        return false;
    }
}
