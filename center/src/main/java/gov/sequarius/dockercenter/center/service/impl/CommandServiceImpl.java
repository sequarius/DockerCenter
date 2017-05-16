package gov.sequarius.dockercenter.center.service.impl;

import gov.sequarius.dockercenter.center.common.Constant;
import gov.sequarius.dockercenter.center.domain.JobConfig;
import gov.sequarius.dockercenter.center.service.CenterService;
import gov.sequarius.dockercenter.center.service.CommandService;
import gov.sequarius.dockercenter.center.support.DockerContext;
import gov.sequarius.dockercenter.center.thrift.sever.CenterAsynThriftServer;
import gov.sequarius.dockercenter.center.util.TagUtil;
import gov.sequarius.dockercenter.common.rpc.CommandDTO;
import gov.sequarius.dockercenter.common.rpc.ExecuteResultDTO;
import gov.sequarius.dockercenter.common.rpc.NodeInfoDTO;
import gov.sequarius.dockercenter.common.rpc.NodeRPCService;
import lombok.extern.slf4j.Slf4j;
import org.apache.thrift.TException;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

/**
 * Created by Sequarius on 2017/3/20.
 */
@Service
@Slf4j
public class CommandServiceImpl implements CommandService {
    @Resource
    DockerContext dockerContext;

    @Resource
    CenterAsynThriftServer centerAsynThriftServer;

    @Resource
    CenterService centerService;

    Map<Integer,CountDownLatch> downLatchMap;

    Map<Integer,ExecuteResultDTO> resultMaps;

    @PostConstruct
    private void init(){
        downLatchMap=new HashMap<>();
        resultMaps=new HashMap<>();
    }

    @Override
    public ExecuteResultDTO callCommand(CommandDTO command) {
        log.debug("command=={}",command);
        dockerContext.addCommand(command);
        Integer tag = command.getNodeTag();
        if (tag == null) {
            tag = Constant.LOCAL_NODE_TAG;
        }
        NodeInfoDTO nodeInfoDTO=centerService.findNodeByTag(tag);
        ExecuteResultDTO executeResultDTO = new ExecuteResultDTO();
        if(nodeInfoDTO==null){
            executeResultDTO.setReturnMessage(String.join("","cant not find node tag ",
                    String.valueOf(tag),"try use dockerc --node-list to confirm the right tag."));
            return executeResultDTO;
        }
        log.debug("node =={}",nodeInfoDTO);
        NodeRPCService.Client client = centerAsynThriftServer.selectClientByIp(nodeInfoDTO.getIp());
        if(client==null) {
            executeResultDTO.setReturnMessage(new StringBuilder("cant find node ").append(nodeInfoDTO.getIp()).append
                    (",try use dockerc --node-list to confirm node status").toString());
            return executeResultDTO;
        }
        command.setCommandTag(TagUtil.gennerateCommandTag(nodeInfoDTO.getTag()));
        try {
            final CountDownLatch latch = new CountDownLatch(1);
            downLatchMap.put(command.getCommandTag(),latch);
            client.exctueCommand(command);
            latch.await();
            executeResultDTO=resultMaps.get(command.getCommandTag());
        } catch (TException e) {
            log.error(e.getMessage(),e);
            executeResultDTO.setReturnMessage("cant connect to the node ,try again please.");
        } catch (InterruptedException e) {
            executeResultDTO.setReturnMessage("node lose response,try again please.");
            log.error(e.getMessage(),e);
        }finally {
            downLatchMap.remove(command.getCommandTag());
            resultMaps.remove(command.getCommandTag());
        }
        return executeResultDTO;
    }

    @Override
    public void onCommandExecuted(ExecuteResultDTO resultDTO) {
        log.debug("result dot=={}",resultDTO);
        CountDownLatch countDownLatch = downLatchMap.get(resultDTO.getCommandTag());
        if(resultDTO==null){
            log.warn("received an unknown result=={}",resultDTO);
            return;
        }
        resultMaps.put(resultDTO.getCommandTag(),resultDTO);
        countDownLatch.countDown();
    }

    @Override
    public void startContainer(Integer minNodeTag, String nextContainer) {

    }

    @Override
    public void stopContainer(Integer maxNodeTag) {

    }

    @Override
    public void startJob(JobConfig jobConfig) {

    }

    @Override
    public void stoptJob(Long jobid) {

    }


}
