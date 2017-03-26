package gov.sequarius.dockercenter.node.thrift.service.impl;

import gov.sequarius.dockercenter.common.rpc.CommandDTO;
import gov.sequarius.dockercenter.common.rpc.ExecuteResultDTO;
import gov.sequarius.dockercenter.node.thrift.service.CommandService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;

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
        LinkedList<String> params = new LinkedList<>(command.getParams());
        params.addFirst("docker");

        String[] paramArray = new String[params.size()];
        int index = 0;
        for (String param : params) {
            paramArray[index] = param;
            index++;
        }
        Process p = null;
        try {
            p = Runtime.getRuntime().exec(paramArray);
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
                sb.append(consoleLine);
            }
            resultDTO.setResultCode(p.exitValue());
            resultDTO.setReturnMessage(sb.toString());
            return resultDTO;
        } catch (IOException e) {
            resultDTO.setResultCode(-1);
            resultDTO.setReturnMessage(e.getMessage());
            log.warn(e.getMessage(), e);
            return resultDTO;
        }

    }
}
