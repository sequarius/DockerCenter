package gov.sequarius.dockercenter.center.stratege;

import gov.sequarius.dockercenter.center.domain.NodeInfo;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Sequarius on 2017/5/7.
 */
@Slf4j
public class RandomStrategy extends DeployStrategy{

    @Override
    public void startContainer() {
        Random random=new Random();
        int nodeCount = nodeInfos.size();
        if(nodeCount ==0){
            return;
        }
        int node=0;
        while (node==0){
            int nexNodeTag=random.nextInt(nodeCount+1)+1;
            if(nodeInfos.get(nexNodeTag).isAvailable()){
                node=nexNodeTag;
            }
        }
        commandService.startContainer(node,nameStrategy.getNextContainer(imageName));
    }

    @Override
    public void stopContainer() {
        List<NodeInfo> imageNodeInfo=new ArrayList<>();
        for (NodeInfo nodeInfo : nodeInfos) {
            if(nodeInfo.getContainerName().contains(imageName)){
                imageNodeInfo.add(nodeInfo);
            }
        }
        if(imageNodeInfo.size()==0){
            log.warn("cant stop container because cant find container running instance");
            return;
        }
        int index=new Random().nextInt(imageNodeInfo.size());
        commandService.stopContainer(imageNodeInfo.get(index).getTag());
    }
}
