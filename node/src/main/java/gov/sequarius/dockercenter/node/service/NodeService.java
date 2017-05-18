package gov.sequarius.dockercenter.node.service;

/**
 * Created by Sequarius on 2017/3/26.
 */
public interface NodeService {
    Boolean updateNodeInfo();

    Boolean updateLog(Long truncateTime, String containerId);
}
