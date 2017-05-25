package gov.sequarius.dockercenter.center.service;

import gov.sequarius.dockercenter.center.domain.JobConfig;
import gov.sequarius.dockercenter.common.rpc.CommonResultDTO;
import gov.sequarius.dockercenter.common.rpc.JobDTO;

import java.util.List;

/**
 * Created by Sequarius on 2017/4/7.
 */
public interface JobService {
    CommonResultDTO createJob(String jobName);
    JobConfig getJob(String jobName);
    List<JobConfig> getJobs();

    JobDTO convertJobConfig(JobConfig jobConfig);

    JobDTO getJobStatus(String jobName);

    CommonResultDTO startJob(String jobName);

    CommonResultDTO stopJob(String jobName);
}
