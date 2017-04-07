package gov.sequarius.dockercenter.center.service;

import gov.sequarius.dockercenter.common.rpc.CommonResultDTO;

/**
 * Created by Sequarius on 2017/4/7.
 */
public interface JobService {
    CommonResultDTO createJob(String s);
}
