package gov.sequarius.dockercenter.node;

import gov.sequarius.dockercenter.common.rpc.CenterAsynRPCService;
import gov.sequarius.dockercenter.common.rpc.CenterSynRPCService;
import lombok.extern.slf4j.Slf4j;
import org.apache.thrift.TException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class DockerCenterNodeApplicationTests {
    @Resource
    CenterSynRPCService.Client centerSynClient;
    @Resource
    CenterAsynRPCService.Client centerAsynClient;

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


    @Test
    public void testAsynClient() throws TException, InterruptedException {
        centerAsynClient.connet();
        while (true){
            Thread.sleep(5000);
        }
    }
}
