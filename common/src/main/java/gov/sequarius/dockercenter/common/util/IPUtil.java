package gov.sequarius.dockercenter.common.util;

import sun.net.util.IPAddressUtil;

/**
 * Created by Sequarius on 2017/3/20.
 */
public class IPUtil {

    public static boolean isLegalIP(String ip){
        return IPAddressUtil.isIPv4LiteralAddress(ip)||IPAddressUtil.isIPv6LiteralAddress(ip);
    }
}
