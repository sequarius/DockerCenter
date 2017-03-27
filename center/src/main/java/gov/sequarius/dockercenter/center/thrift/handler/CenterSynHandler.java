package gov.sequarius.dockercenter.center.thrift.handler;


import gov.sequarius.dockercenter.center.service.CenterService;
import gov.sequarius.dockercenter.center.service.CommandService;
import gov.sequarius.dockercenter.common.domain.CommonResult;
import gov.sequarius.dockercenter.common.rpc.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.thrift.TException;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;

/**
 * Created by Sequarius on 2017/3/14.
 */
@Slf4j
@Component
public class CenterSynHandler implements CenterSynRPCService.Iface {
    @Resource
    CenterService centerService;

    @Resource
    CommandService commandService;



    @Override
    public CommonResultDTO registerNode(NodeInfoDTO nodeInfoDTO, String s) throws TException {
        log.debug("node infoDTO=={},code=={}",nodeInfoDTO,s);
        CommonResult commonResult = centerService.registerNodeInfo(nodeInfoDTO);
        CommonResultDTO commonResultDTO = new CommonResultDTO();
        BeanUtils.copyProperties(commonResult, commonResultDTO);

        return commonResultDTO;
    }

    @Override
    public CommonResultDTO updateNodeInfo(NodeInfoDTO nodeInfoDTO) throws TException {
        CommonResult commonResult = centerService.updateNodeInfo(nodeInfoDTO);
        CommonResultDTO commonResultDTO = new CommonResultDTO();
        BeanUtils.copyProperties(commonResult, commonResultDTO);

        return commonResultDTO;
    }

    @Override
    public CommonResultDTO removeNode() throws TException {
        CommonResult commonResult = centerService.removeNodeInfoByTag();
        CommonResultDTO commonResultDTO = new CommonResultDTO();
        BeanUtils.copyProperties(commonResult, commonResultDTO);
        return commonResultDTO;
    }

    @Override
    public Map<String, NodeInfoDTO> getNodeMap() throws TException {
        return centerService.getNodeInfoMap();
    }

    @Override
    public ExecuteResultDTO executeCommand(CommandDTO commandDTO) throws TException {
        return commandService.callCommand(commandDTO);
    }


}
