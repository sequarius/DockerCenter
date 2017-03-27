package gov.sequarius.dockercenter.node.thrift.service.impl;

import com.sun.management.OperatingSystemMXBean;
import gov.sequarius.dockercenter.common.rpc.CenterSynRPCService;
import gov.sequarius.dockercenter.common.rpc.CommonResultDTO;
import gov.sequarius.dockercenter.common.rpc.NodeInfoDTO;
import gov.sequarius.dockercenter.node.thrift.service.NodeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.thrift.TException;
import org.apache.thrift.transport.TSocket;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.lang.management.ManagementFactory;
import java.util.Properties;

/**
 * Created by Sequarius on 2017/3/26.
 */
@Service
@Slf4j
public class NodeServiceImpl implements NodeService {
    @Resource
    CenterSynRPCService.Client centerSynClient;

    @Resource
    TSocket tSocket;

    private NodeInfoDTO nodeInfoDTO;

    public Boolean updateNodeInfo() {
        NodeInfoDTO nodeInfoDTO = new NodeInfoDTO();

        Properties props = System.getProperties();
        String osName = props.getProperty("os.name");
        String osVersion = props.getProperty("os.version");
        String osArch = props.getProperty("os.arch");
        OperatingSystemMXBean systemMXBean = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
        nodeInfoDTO.setFreeDiskSpace(systemMXBean.getFreeSwapSpaceSize());
        nodeInfoDTO.setFreeMemorySpace(systemMXBean.getFreePhysicalMemorySize());
        nodeInfoDTO.setCallTime(System.currentTimeMillis());
        nodeInfoDTO.setArchitecture(String.join("/", osName, osVersion, osArch));

        try {
            tSocket.open();
            CommonResultDTO commonResultDTO = centerSynClient.updateNodeInfo(nodeInfoDTO);
            if (commonResultDTO.isResult()) {
                return true;
            } else {
                log.warn("cant update node_info {},message=={}", nodeInfoDTO, commonResultDTO);
                return false;
            }
        } catch (TException e) {
            log.warn(e.getMessage(), e);
            return false;
        }finally {
            tSocket.close();
        }
    }
}
