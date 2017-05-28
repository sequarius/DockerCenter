package gov.sequarius.dockercenter.node.service.impl;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.CreateContainerResponse;
import gov.sequarius.dockercenter.common.rpc.CommandDTO;
import gov.sequarius.dockercenter.common.rpc.ExecuteResultDTO;
import gov.sequarius.dockercenter.node.service.CommandService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sequarius on 2017/3/26.
 */
@Slf4j
@Service
public class CommandServiceImp implements CommandService {
    @Resource
    DockerClient dockerClient;

    @Override
    public ExecuteResultDTO executeCommandOnNode(CommandDTO command) {
        ExecuteResultDTO resultDTO = new ExecuteResultDTO();
        resultDTO.setCommandTag(command.getCommandTag());
        resultDTO.setNodeTag(command.getNodeTag());
        List<String> params = command.getParams();
        if (params == null) {
            params = new ArrayList<>();
            command.setParams(params);
        }

        if (command.getCommand().equals("run")) {
            String image = params.remove(params.size() - 1);
            log.debug("image=={},params=={}",image,params);
            CreateContainerResponse response = dockerClient.createContainerCmd(image).exec();
            String id = response.getId();
            dockerClient.startContainerCmd(id).exec();
            resultDTO.setReturnMessage(id);
            return resultDTO;
        }

        StringBuilder commandBuilder = new StringBuilder("docker");
        commandBuilder.append(" ").append(command.getCommand());
        for (String s : params) {
            commandBuilder.append(" ").append(s);
        }
        String[] shellCommand;
        if ("Windows".equals(System.getProperty("os.name"))) {
            shellCommand = new String[]{"cmd", "/c", commandBuilder.toString()};
        } else {
            shellCommand = new String[]{"/bin/sh", "-c", commandBuilder.toString()};
        }
        Process p = null;
        try {
            if (!"info".equals(command.getCommand())) {
                log.debug("command array=={}", commandBuilder.toString());
            }
            p = Runtime.getRuntime().exec(shellCommand);
        } catch (IOException e) {
            resultDTO.setResultCode(-1);
            resultDTO.setReturnMessage(e.getMessage());
            log.warn(e.getMessage(), e);
            return resultDTO;
        }
        try (BufferedInputStream in = new BufferedInputStream(p.getInputStream());
             BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in))
        ) {
            StringBuilder sb = new StringBuilder();
            String consoleLine;
            while ((consoleLine = bufferedReader.readLine()) != null) {
                sb.append(consoleLine).append("\n");
            }
            if (!"info".equals(command.getCommand())) {
                log.debug("wait before");
            }
            p.waitFor();
            if (!"info".equals(command.getCommand())) {
                log.debug("wait after");
            }
            resultDTO.setResultCode(p.exitValue());
            resultDTO.setReturnMessage(sb.toString());
            return resultDTO;
        } catch (IOException e) {
            resultDTO.setResultCode(-1);
            resultDTO.setReturnMessage(e.getMessage());
            log.warn(e.getMessage(), e);
            return resultDTO;
        } catch (InterruptedException e) {
            resultDTO.setResultCode(-1);
            resultDTO.setReturnMessage(e.getMessage());
            log.warn(e.getMessage(), e);
            return resultDTO;
        }

    }

}
