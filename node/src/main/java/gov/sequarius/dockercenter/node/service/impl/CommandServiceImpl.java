package gov.sequarius.dockercenter.node.service.impl;

import gov.sequarius.dockercenter.common.rpc.CommandDTO;
import gov.sequarius.dockercenter.common.rpc.ExecuteResultDTO;
import gov.sequarius.dockercenter.node.service.CommandService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by Sequarius on 2017/3/26.
 */
@Service
@Slf4j
public class CommandServiceImpl implements CommandService {
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
