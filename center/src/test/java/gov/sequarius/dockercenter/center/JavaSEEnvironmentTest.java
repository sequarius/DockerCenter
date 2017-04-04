package gov.sequarius.dockercenter.center;

import gov.sequarius.dockercenter.common.util.IPUtil;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Sequarius on 2017/3/20.
 */
public class JavaSEEnvironmentTest {
    @Test
    public void testIPUtil(){
        Assert.assertTrue(IPUtil.isLegalIP("192.168.1.1"));
        Assert.assertTrue(IPUtil.isLegalIP("119.6.126.169"));
        Assert.assertTrue(IPUtil.isLegalIP("fe80:0000:0000:0000:0204:61ff:fe9d:f156"));
        Assert.assertTrue(IPUtil.isLegalIP("fe80:0000:0000:0000:0204:61ff:fe9d:f156"));
        Assert.assertTrue(IPUtil.isLegalIP("fe80::204:61ff:fe9d:f156"));
        Assert.assertTrue(IPUtil.isLegalIP("fe80:0:0:0:0204:61ff:254.157.241.86"));
        Assert.assertTrue(IPUtil.isLegalIP("fe80::204:61ff:254.157.241.86"));
        Assert.assertTrue(IPUtil.isLegalIP("fe80::"));
        Assert.assertTrue(IPUtil.isLegalIP("2001::"));
        Assert.assertFalse(IPUtil.isLegalIP("267.267.267.267"));
        Assert.assertFalse(IPUtil.isLegalIP("0.267.267.267"));
    }

}
