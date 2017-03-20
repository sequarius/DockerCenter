package gov.sequarius.dockercenter.center;

import gov.sequarius.dockercenter.center.service.CenterService;
import gov.sequarius.dockercenter.common.domain.CommonResult;
import gov.sequarius.dockercenter.common.rpc.NodeInfoDTO;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * Created by Sequarius on 2017/3/20.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class CenterServiceTest {
    public static final String TEST_IP = "192.168.1.2";
    @Resource
    CenterService centerService;

    private Integer startNodeCount;

    @Test
    @Before
    public void contextLoad(){
        startNodeCount =centerService.getNodeInfoMap().size();
        log.debug("start node count=={}",startNodeCount);
    }

    @Test
    public void testAddNode(){
        NodeInfoDTO node=new NodeInfoDTO();
        node.setName("FOO");
        node.setIp(TEST_IP);
        CommonResult commonResult = centerService.registerNodeInfo(node);
        Assert.assertTrue(commonResult.getResult());
        Assert.assertEquals(startNodeCount.longValue(),centerService.getNodeInfoMap().size()-1);
    }

    @Test
    public void testRemoveNode(){
        CommonResult commonResult = centerService.removeNodeInfoByIp(TEST_IP);
        Assert.assertTrue(commonResult.getResult());
        Assert.assertEquals(startNodeCount.longValue()-1,centerService.getNodeInfoMap().size());
    }
}
