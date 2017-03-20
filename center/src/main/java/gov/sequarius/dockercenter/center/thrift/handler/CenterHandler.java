package gov.sequarius.dockercenter.center.thrift.handler;


import gov.sequarius.dockercenter.center.common.Constant;
import gov.sequarius.dockercenter.center.service.CenterService;
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
public class CenterHandler implements CenterRPCService.Iface {

    @Resource
    CenterService centerService;

    @Override
    public CommonResultDTO registerNode(NodeInfoDTO nodeInfoDTO, String s) throws TException {
        CommonResult commonResult = centerService.registerNodeInfo(nodeInfoDTO);
        CommonResultDTO commonResultDTO = new CommonResultDTO();
        BeanUtils.copyProperties(commonResult, commonResultDTO);
        return commonResultDTO;
    }

    @Override
    public CommonResultDTO removeNode(String ip) throws TException {
        CommonResult commonResult = centerService.removeNodeInfoByIp(ip);
        CommonResultDTO commonResultDTO = new CommonResultDTO();
        BeanUtils.copyProperties(commonResult, commonResultDTO);
        return commonResultDTO;
    }

    @Override
    public Map<String, NodeInfoDTO> getNodeMap() throws TException {
        return centerService.getNodeInfoMap();
    }

    @Override
    public ExecuteResultDTO excuteCommand(CommandDTO commandDTO) throws TException {
        Integer tag = commandDTO.getNodeTag();
        if (tag == null) {
            tag = Constant.LOCAL_NODE_TAG;
        }
        NodeInfoDTO nodeInfoDTO=centerService.findNodeByTag(tag);
        //todo
        return null;
    }


}
