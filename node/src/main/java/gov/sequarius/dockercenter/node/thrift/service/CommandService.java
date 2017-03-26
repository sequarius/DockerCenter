package gov.sequarius.dockercenter.node.thrift.service;

import gov.sequarius.dockercenter.common.rpc.CommandDTO;
import gov.sequarius.dockercenter.common.rpc.ExecuteResultDTO;

/**
 * Created by Sequarius on 2017/3/26.
 */
public interface CommandService {
    ExecuteResultDTO executeCommandOnNode(CommandDTO command);
}
