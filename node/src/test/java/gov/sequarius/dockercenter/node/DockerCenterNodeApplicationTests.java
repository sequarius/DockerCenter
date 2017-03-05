package gov.sequarius.dockercenter.node;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class DockerCenterNodeApplicationTests {

    @Test
    public void contextLoads() throws IOException, InterruptedException {
        Process p = Runtime.getRuntime().exec(new String[]{"ping", "baidu.com"});
        try (BufferedInputStream in = new BufferedInputStream(p.getInputStream());
             BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in))
        ) {
            String lineStr;
            while ((lineStr = bufferedReader.readLine()) != null){
                System.out.println(lineStr);
            }if (p.waitFor() != 0){
                if (p.exitValue() != 0){
                    log.debug("执行失败");
                }
            }
        }

    }

}
