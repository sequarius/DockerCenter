package gov.sequarius.dockercenter.center.service;

import gov.sequarius.dockercenter.center.domain.DockerCommand;
import gov.sequarius.dockercenter.common.domain.ExecuteResult;

/**
 * Created by Sequarius on 2017/3/20.
 */
public interface CommandService {
    ExecuteResult callCommand(DockerCommand command);
}
