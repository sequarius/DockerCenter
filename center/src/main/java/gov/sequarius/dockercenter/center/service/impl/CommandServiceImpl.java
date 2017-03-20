package gov.sequarius.dockercenter.center.service.impl;

import gov.sequarius.dockercenter.center.domain.DockerCommand;
import gov.sequarius.dockercenter.center.service.CommandService;
import gov.sequarius.dockercenter.center.support.DockerContext;
import gov.sequarius.dockercenter.common.domain.ExecuteResult;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by Sequarius on 2017/3/20.
 */
@Service
public class CommandServiceImpl implements CommandService {
    @Resource
    DockerContext dockerContext;

    @Override
    public ExecuteResult callCommand(DockerCommand command) {
        dockerContext.addCommand(command);

        return null;
    }
}
