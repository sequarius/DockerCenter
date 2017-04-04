package gov.sequarius.dockercenter.center.domain;

import lombok.Getter;

import java.util.List;

/**
 * Created by Sequarius on 2017/3/20.
 */
@Getter
public class DockerCommand {
    private String format;
    List<String> args;
    private String command;

    public DockerCommand(String format, List<String> args) {
        this.format = format;
        this.args = args;
        if (args == null || args.size() < 1) {
            command =format;
        }else{
            command=String.format(format,args);
        }
    }

    public DockerCommand(String command){
        new DockerCommand(command,null);
    }
}
