package gov.sequarius.dockercenter.center.thrift.handler;

import gov.sequarius.dockercenter.common.rpc.CenterService;
import gov.sequarius.dockercenter.common.rpc.NodeInfoDTO;
import gov.sequarius.dockercenter.common.rpc.ResultDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.thrift.TException;
import org.springframework.stereotype.Component;

/**
 * Created by Sequarius on 2017/3/14.
 */
@Slf4j
@Component
public class CenterHandler implements CenterService.Iface {
    @Override
    public ResultDTO registerNode(NodeInfoDTO nodeInfoDTO, String s) throws TException {
        log.debug("here");
        return null;
    }
}
