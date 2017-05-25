package gov.sequarius.dockercenter.node;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.model.Info;
import com.github.dockerjava.core.DockerClientBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Sequarius on 2017/5/22.
 */
@Slf4j
@Configuration
public class DockerClientBean {
    @Bean
    public DockerClient dockerClient(){
        DockerClient client = DockerClientBuilder.getInstance("tcp://localhost:2375").build();
        Info info = client.infoCmd().exec();
        log.debug("into here info =={},",info);
        return client;
    }
}
