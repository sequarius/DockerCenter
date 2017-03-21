package gov.sequarius.dockercenter.center.thrift.handler;

import gov.sequarius.dockercenter.common.rpc.CenterAsynRPCService;
import gov.sequarius.dockercenter.common.rpc.ExecuteResultDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.thrift.TException;
import org.springframework.stereotype.Component;

/**
 * Created by Sequarius on 2017/3/21.
 */
@Component
@Slf4j
public class CenterAsynHandler implements CenterAsynRPCService.Iface {
    @Override
    public void connet() throws TException {
        log.debug("connect");
    }

    @Override
    public void onCommandExcuteFinish(ExecuteResultDTO executeResultDTO) throws TException {
        log.debug("con commnand finish {}",executeResultDTO);
    }
}
