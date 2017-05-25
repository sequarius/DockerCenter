package gov.sequarius.dockercenter.center.stratege;

import gov.sequarius.dockercenter.center.domain.NodeInfo;

/**
 * Created by Sequarius on 2017/5/7.
 */
public class NodePreferStrategy extends DeployStrategy{
    private DeployStrategy spareStrategy;
    private Integer preferNode;
    @Override
    public void startContainer() {
        if(nodeInfos.get(preferNode).isAvailable()) {
            commandService.startContainer(nodeInfos.get(preferNode).getTag(), nameStrategy.getNextContainer(imageName));
        }
        spareStrategy.startContainer();
    }

    @Override
    public void stopContainer() {
        for (NodeInfo nodeInfo : nodeInfos) {
            if(nodeInfo.getContainerName().contains(imageName)&&nodeInfo.getTag()==preferNode){
                commandService.stopContainer(preferNode);
            }
        }
        commandService.stopContainer(preferNode);
    }
}
