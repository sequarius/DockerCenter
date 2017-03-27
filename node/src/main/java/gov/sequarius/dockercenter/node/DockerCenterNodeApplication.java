package gov.sequarius.dockercenter.node;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class DockerCenterNodeApplication {

	public static void main(String[] args) {
		SpringApplication.run(DockerCenterNodeApplication.class, args);
	}
}
