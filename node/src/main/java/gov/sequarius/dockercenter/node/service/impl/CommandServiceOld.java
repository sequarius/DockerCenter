package gov.sequarius.dockercenter.node.service.impl;

import com.github.dockerjava.api.DockerClient;
import gov.sequarius.dockercenter.common.rpc.CommandDTO;
import gov.sequarius.dockercenter.common.rpc.ExecuteResultDTO;
import gov.sequarius.dockercenter.node.service.CommandService;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;
import java.util.ArrayList;

/**
 * Created by Sequarius on 2017/3/26.
 */
@Slf4j
public class CommandServiceOld implements CommandService {
    @Resource
    DockerClient dockerClient;

    @Override
    public ExecuteResultDTO executeCommandOnNode(CommandDTO command) {
        ExecuteResultDTO resultDTO = new ExecuteResultDTO();
        resultDTO.setCommandTag(command.getCommandTag());
        resultDTO.setNodeTag(command.getNodeTag());
        if (command.getParams() == null) {
            command.setParams(new ArrayList<>());
        }
        StringBuilder commandBuilder = new StringBuilder("docker");
        commandBuilder.append(" ").append(command.getCommand());
        for (String s : command.getParams()) {
            commandBuilder.append(" ").append(s);
        }
        return resultDTO;

    }
}
