package gov.sequarius.dockercenter.node.schedule;

import gov.sequarius.dockercenter.node.service.NodeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Created by Sequarius on 2017/3/27.
 */
@Component
@Slf4j
public class SynchronizeSchedule {
    @Resource
    NodeService nodeService;
    @Scheduled(cron="0/5 * *  * * ? ")
    public void updateNodeInfo(){
        Boolean result = nodeService.updateNodeInfo();
        if(!result){
           log.warn("update node info failed");
        }
    }
}
