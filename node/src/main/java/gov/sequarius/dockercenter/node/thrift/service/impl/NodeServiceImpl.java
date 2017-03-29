package gov.sequarius.dockercenter.node.thrift.service.impl;

import com.sun.management.OperatingSystemMXBean;
import gov.sequarius.dockercenter.common.rpc.*;
import gov.sequarius.dockercenter.node.thrift.service.CommandService;
import gov.sequarius.dockercenter.node.thrift.service.NodeService;
import gov.sequarius.dockercenter.node.thrift.util.GrepUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.thrift.TException;
import org.apache.thrift.transport.TSocket;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.lang.management.ManagementFactory;
import java.util.Map;
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

    @Resource
    CommandService commandService;

    @PostConstruct
    private void init(){
        nodeInfoDTO = new NodeInfoDTO();
        Properties props = System.getProperties();
        String osName = props.getProperty("os.name");
        String osVersion = props.getProperty("os.version");
        String osArch = props.getProperty("os.arch");
        nodeInfoDTO.setArchitecture(String.join("/", osName, osVersion, osArch));



        try {
            tSocket.open();
            CommonResultDTO commonResultDTO = centerSynClient.registerNode(nodeInfoDTO, "544484");
            log.info("register node {}",commonResultDTO);
        } catch (TException e) {
            log.warn(e.getMessage(),e);
        }finally {
            tSocket.close();
        }
    }

    public Boolean updateNodeInfo() {
        OperatingSystemMXBean systemMXBean = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
        CommandDTO commandDTO=new CommandDTO();
        commandDTO.setCommand("info");

        ExecuteResultDTO resultDTO = commandService.executeCommandOnNode(commandDTO);
        if(resultDTO.getResultCode()==0) {
            Map<String, String> infoMap = GrepUtil.grepDockerInfo(resultDTO.getReturnMessage());
            log.debug("return map=={}",infoMap);
            nodeInfoDTO.setDockerVersion(infoMap.get("Server Version"));
            nodeInfoDTO.setContainerCount(Long.valueOf(infoMap.get("Containers")));
            nodeInfoDTO.setRunningContainerCount(Long.valueOf(infoMap.get("Running")));
        }else{
            nodeInfoDTO.setDockerVersion("UNKNOWN");
        }
        nodeInfoDTO.setFreeDiskSpace(systemMXBean.getFreeSwapSpaceSize());
        nodeInfoDTO.setFreeMemorySpace(systemMXBean.getFreePhysicalMemorySize());
        nodeInfoDTO.setCallTime(System.currentTimeMillis());
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
