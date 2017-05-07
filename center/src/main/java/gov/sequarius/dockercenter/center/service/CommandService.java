package gov.sequarius.dockercenter.center.service;

import gov.sequarius.dockercenter.common.rpc.CommandDTO;
import gov.sequarius.dockercenter.common.rpc.ExecuteResultDTO;

/**
 * Created by Sequarius on 2017/3/20.
 */
public interface CommandService {
    ExecuteResultDTO callCommand(CommandDTO command);
    void onCommandExecuted(ExecuteResultDTO result);

    void startContainer(Integer minNodeTag, String nextContainer);

    void stopContainer(Integer maxNodeTag);
}
