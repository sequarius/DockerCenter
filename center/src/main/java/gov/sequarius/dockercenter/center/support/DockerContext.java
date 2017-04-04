package gov.sequarius.dockercenter.center.support;

import gov.sequarius.dockercenter.common.rpc.CommandDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayDeque;
import java.util.Queue;

/**
 * Created by Sequarius on 2017/3/20.
 */
@Component
public class DockerContext {
    @Value("${gov.sequarius.docker.center.max-history-record}")
    private Integer MAX_HISTORY_RECORD;
    Queue<CommandDTO> commandsHistories;

    @PostConstruct
    private void init() {
        commandsHistories = new ArrayDeque<>(MAX_HISTORY_RECORD / 4);
    }

    public void addCommand(CommandDTO command) {
        while (commandsHistories.size() >= MAX_HISTORY_RECORD) {
            CommandDTO dockerCommand = commandsHistories.poll();
            if (dockerCommand == null) {
                break;
            }
        }
        commandsHistories.add(command);
    }
}
