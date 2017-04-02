package gov.sequarius.dockercenter.node;

import com.github.javafaker.Faker;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Sequarius on 2017/4/2.
 */
@Configuration
public class FackerBean {
    @Bean
    public Faker faker(){
        return new Faker();
    }
}
