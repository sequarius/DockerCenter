package gov.sequarius.dockercenter.center.service;

import gov.sequarius.dockercenter.common.rpc.NodeInfoDTO;

import java.util.List;

/**
 * Created by Sequarius on 2017/3/17.
 */
public interface CenterService {
    List<NodeInfoDTO> getNodeInfoDtos();
    boolean registerNodeInfo(NodeInfoDTO nodeInfoDTO);
}
