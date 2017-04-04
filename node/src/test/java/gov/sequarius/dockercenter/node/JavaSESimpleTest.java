package gov.sequarius.dockercenter.node;

import org.junit.Test;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by Sequarius on 2017/3/26.
 */

public class JavaSESimpleTest {
    @Test
    public void test() throws UnknownHostException {
        InetAddress address = InetAddress.getByName("www.weibo.com");

        System.out.println(address.isLoopbackAddress());

    }
}
