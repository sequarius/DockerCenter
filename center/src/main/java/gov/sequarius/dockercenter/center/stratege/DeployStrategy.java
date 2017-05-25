package gov.sequarius.dockercenter.center.stratege;

import gov.sequarius.dockercenter.center.domain.NodeInfo;
import gov.sequarius.dockercenter.center.service.CommandService;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Sequarius on 2017/5/7.
 */
public abstract class DeployStrategy {
    protected List<NodeInfo> nodeInfos;
    protected String imageName;
    protected ContainerNameStrategy nameStrategy;
    @Resource
    CommandService commandService;
    abstract void startContainer();
    abstract void stopContainer();
}
