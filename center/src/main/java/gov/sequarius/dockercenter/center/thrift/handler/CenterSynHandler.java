package gov.sequarius.dockercenter.center.thrift.handler;


import gov.sequarius.dockercenter.center.domain.JobConfig;
import gov.sequarius.dockercenter.center.service.CenterService;
import gov.sequarius.dockercenter.center.service.CommandService;
import gov.sequarius.dockercenter.center.service.JobService;
import gov.sequarius.dockercenter.common.domain.CommonResult;
import gov.sequarius.dockercenter.common.rpc.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.thrift.TException;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
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

    @Resource
    JobService jobService;



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

    @Override
    public CommonResultDTO newJob(String jobName) throws TException {
        return jobService.createJob(jobName);
    }

    @Override
    public JobDTO getJobStatus(String jobName) throws TException {
        return jobService.getJobStatus(jobName);
    }

    @Override
    public CommonResultDTO startJob(String jobName) throws TException {
        return jobService.startJob(jobName);
    }

    @Override
    public CommonResultDTO stopJob(String jobName) throws TException {
        return jobService.stopJob(jobName);
    }

    @Override
    public List<JobDTO> getJoblist() throws TException {
        List<JobDTO> jobDTOs=new ArrayList<>();
        List<JobConfig> jobs = jobService.getJobs();
        for (JobConfig job : jobs) {
            jobDTOs.add(jobService.convertJobConfig(job));
        }
        return jobDTOs;
    }

    @Override
    public CommonResultDTO uploadLog(ByteBuffer byteBuffer, int i) throws TException {
        return null;
    }


}
