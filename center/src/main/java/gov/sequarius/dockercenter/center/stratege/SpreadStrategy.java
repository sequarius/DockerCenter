package gov.sequarius.dockercenter.center.stratege;

import gov.sequarius.dockercenter.center.domain.NodeInfo;

/**
 * Created by Sequarius on 2017/5/7.
 */
public class SpreadStrategy extends DeployStrategy{

    @Override
    public void startContainer() {
        Long minLoad=Long.MAX_VALUE;
        Integer minNodeTag=0;
        for (NodeInfo nodeInfo : nodeInfos) {
            if(nodeInfo.getLoad().compareTo(minLoad)<0){
                minLoad=nodeInfo.getLoad();
                minNodeTag=nodeInfo.getTag();
            }
        }
        commandService.startContainer(minNodeTag,nameStrategy.getNextContainer(imageName));
    }

    @Override
    public void stopContainer() {
        Long maxLoad=Long.MIN_VALUE;
        Integer maxNodeTag=0;
        for (NodeInfo nodeInfo : nodeInfos) {
            if(nodeInfo.getLoad().compareTo(maxLoad)>0&&nodeInfo.getContainerName().contains(imageName)){
                maxLoad=nodeInfo.getLoad();
                maxNodeTag=nodeInfo.getTag();
            }
        }
        commandService.stopContainer(maxNodeTag);
    }
}
