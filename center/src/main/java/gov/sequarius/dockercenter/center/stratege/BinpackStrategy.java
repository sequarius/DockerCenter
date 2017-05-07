package gov.sequarius.dockercenter.center.stratege;

import gov.sequarius.dockercenter.center.domain.NodeInfo;

/**
 * Created by Sequarius on 2017/5/7.
 */
public class BinpackStrategy extends DeployStrategy{

    @Override
    public void startContainer() {
        Long maxLoad=Long.MIN_VALUE;
        Integer maxNodeTag=0;
        for (NodeInfo nodeInfo : nodeInfos) {
            if(nodeInfo.getLoad().compareTo(maxLoad)>0){
                maxLoad=nodeInfo.getLoad();
                maxNodeTag=nodeInfo.getTag();
            }
        }
        commandService.startContainer(maxNodeTag,nameStrategy.getNextContainer(imageName));
    }

    @Override
    public void stopContainer() {
        Long minLoad=Long.MAX_VALUE;
        Integer minNodeTag=0;
        for (NodeInfo nodeInfo : nodeInfos) {
            if(nodeInfo.getLoad().compareTo(minLoad)<0&&nodeInfo.getContainerName().contains(imageName)){
                minLoad=nodeInfo.getLoad();
                minNodeTag=nodeInfo.getTag();
            }
        }
        commandService.stopContainer(minNodeTag);
    }
}
