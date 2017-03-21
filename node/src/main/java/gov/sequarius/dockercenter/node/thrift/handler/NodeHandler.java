package gov.sequarius.dockercenter.node.thrift.handler;

import gov.sequarius.dockercenter.common.rpc.CenterAsynRPCService;
import gov.sequarius.dockercenter.common.rpc.CommandDTO;
import gov.sequarius.dockercenter.common.rpc.ExecuteResultDTO;
import gov.sequarius.dockercenter.common.rpc.NodeRPCService;
import lombok.extern.slf4j.Slf4j;
import org.apache.thrift.TException;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Created by Sequarius on 2017/3/20.
 */
@Slf4j
@Component
public class NodeHandler implements NodeRPCService.Iface {

    @Resource
    CenterAsynRPCService.Client centerAsynClient;
    @Override
    public void exctueCommand(CommandDTO commandDTO) throws TException {
        log.debug("command into=={}",commandDTO);
        ExecuteResultDTO resultDTO=new ExecuteResultDTO("bbb",0,2333,6666);
        centerAsynClient.onCommandExcuteFinish(resultDTO);
    }
}
