package gov.sequarius.dockercenter.center;

import gov.sequarius.dockercenter.center.service.CenterService;
import gov.sequarius.dockercenter.center.service.JobService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class DockercenterCenterApplicationTests {

	@Resource
	JobService jobService;
	@Resource
	private CenterService centerService;
	@Test
	public void contextLoads() {

	}

	@Test
	public void testGetAll(){
		jobService.createJob("aaa");
    }

}
