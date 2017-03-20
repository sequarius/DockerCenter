package gov.sequarius.dockercenter.center.service;


import gov.sequarius.dockercenter.common.domain.CommonResult;
import gov.sequarius.dockercenter.common.rpc.NodeInfoDTO;

import java.util.Map;

/**
 * Created by Sequarius on 2017/3/17.
 */
public interface CenterService {
    CommonResult registerNodeInfo(NodeInfoDTO nodeInfoDTO);

    CommonResult removeNodeInfoByIp(String ip);

    Map<String, NodeInfoDTO> getNodeInfoMap();

    NodeInfoDTO findNodeByTag(Integer tag);
}
