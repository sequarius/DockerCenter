package gov.sequarius.dockercenter.node.thrift.service.impl;

import com.sun.management.OperatingSystemMXBean;
import gov.sequarius.dockercenter.common.rpc.CenterSynRPCService;
import gov.sequarius.dockercenter.common.rpc.CommonResultDTO;
import gov.sequarius.dockercenter.common.rpc.NodeInfoDTO;
import gov.sequarius.dockercenter.node.thrift.service.NodeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.thrift.TException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.lang.management.ManagementFactory;

/**
 * Created by Sequarius on 2017/3/26.
 */
@Service
@Slf4j
public class NodeServiceImpl implements NodeService{
    @Resource
    CenterSynRPCService.Client centerSynClient;
    public Boolean updateNodeInfo() {
        NodeInfoDTO nodeInfoDTO=new NodeInfoDTO();

        OperatingSystemMXBean systemMXBean = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
        nodeInfoDTO.setFreeDiskSpace(systemMXBean.getFreeSwapSpaceSize());
        nodeInfoDTO.setFreeMemorySpace(systemMXBean.getFreePhysicalMemorySize());
        nodeInfoDTO.setCallTime(System.currentTimeMillis());
        nodeInfoDTO.setArchitecture(systemMXBean.getArch());

        try {
            CommonResultDTO commonResultDTO = centerSynClient.updateNodeInfo(nodeInfoDTO);
            if(commonResultDTO.isResult()){
                return true;
            }else{
                log.warn("cant update node_info {},message=={}",nodeInfoDTO,commonResultDTO);
                return false;
            }
        } catch (TException e) {
            log.warn(e.getMessage(),e);
            return false;
        }
    }
}
