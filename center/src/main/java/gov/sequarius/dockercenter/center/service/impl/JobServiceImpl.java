package gov.sequarius.dockercenter.center.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONReader;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.serializer.SerializerFeature;
import gov.sequarius.dockercenter.center.domain.Condition;
import gov.sequarius.dockercenter.center.domain.JobConfig;
import gov.sequarius.dockercenter.center.domain.JobStatus;
import gov.sequarius.dockercenter.center.domain.Step;
import gov.sequarius.dockercenter.center.service.JobService;
import gov.sequarius.dockercenter.common.rpc.CommonResultDTO;
import gov.sequarius.dockercenter.common.rpc.JobDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Sequarius on 2017/4/7.
 */
@Service
@Slf4j
public class JobServiceImpl implements JobService {
    @Value("${gov.sequarius.docker.center.job-config-path}")
    private String JOB_CONFIG_PATH;


    private File JOB_CONFIG_DIR;

    private Map<String,String> jobStatus;


    @PostConstruct
    private void init() {
        JOB_CONFIG_DIR = new File(JOB_CONFIG_PATH);
        if (!JOB_CONFIG_DIR.exists()) {
            JOB_CONFIG_DIR.mkdir();
        }
        jobStatus=new HashMap<>();
    }


    @Override
    public CommonResultDTO createJob(String jobName) {
        File outputFile = new File(JOB_CONFIG_DIR, jobName + ".json");
        JobConfig jobConfig = new JobConfig();
        CommonResultDTO resultDTO = new CommonResultDTO();
        try (FileOutputStream outputStream = new FileOutputStream(outputFile)) {
            jobConfig.setName(jobName);
            Step startStep = new Step();
            Step endStep = new Step();
            startStep.setCondition(new Condition());
            endStep.setCondition(new Condition());
            jobConfig.setStartStep(startStep);
            jobConfig.setEndStep(endStep);
            outputStream.write(JSON.toJSONString(jobConfig, SerializerFeature.PrettyFormat,SerializerFeature.WriteMapNullValue).getBytes());
        } catch (FileNotFoundException e) {
            log.error(e.getMessage(), e);
            resultDTO.setResult(false);
            resultDTO.setMessage(e.getMessage());
            return resultDTO;
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            resultDTO.setResult(false);
            resultDTO.setMessage(e.getMessage());
            return resultDTO;
        }
        resultDTO.setResult(true);
        resultDTO.setMessage(String.format("config file %s create successfully",outputFile.getPath()));
//        log.debug("file {} write finish", outputFile.getAbsolutePath());
        return resultDTO;
    }

    @Override
    public JobConfig getJob(String jobName){
        File inputFile = new File(JOB_CONFIG_DIR, jobName + ".json");
        return getJobConfig(inputFile);
    }

    @Override
    public List<JobConfig> getJobs() {
        File[] files = JOB_CONFIG_DIR.listFiles();
        List<JobConfig> jobConfigs=new ArrayList<>();
        for (File file : files) {
            if(file.isDirectory()){
                continue;
            }
            jobConfigs.add(getJobConfig(file));
        }
        return jobConfigs;
    }

    @Override
    public JobDTO convertJobConfig(JobConfig job) {
        if(job==null){
            return null;
        }
        JobDTO jobDTO=new JobDTO();
        jobDTO.setJobname(job.getName());
        jobDTO.setJobId(String.valueOf(job.getId()));
        jobDTO.setStatus("running");
        jobDTO.setDeployStrategy(job.getDeployStrategy());
        jobDTO.setSubNameStrategy(job.getSubNameStrategy());
        jobDTO.setConfig(JSON.toJSONString(job));
        jobDTO.setStatus(jobStatus.get(job.getName())==null? JobStatus.CREATE.toString():jobStatus.get(job.getName()));
        return jobDTO;
    }

    @Override
    public JobDTO getJobStatus(String jobName) {
        return convertJobConfig(getJob(jobName));
    }

    @Override
    public CommonResultDTO startJob(String jobName) {
        CommonResultDTO resultDTO=new CommonResultDTO();
        JobDTO job = getJobStatus(jobName);
        if(job==null){
            resultDTO.setResult(false);
            resultDTO.setMessage(String.join(jobName,"job "," is not existed!"));
            return resultDTO;
        }
        if(job.getStatus().equals(JobStatus.RUNNING.toString())){
            resultDTO.setResult(false);
            resultDTO.setMessage(String.join(jobName,"job "," is already running!"));
            return resultDTO;
        }
        this.jobStatus.put(jobName,JobStatus.RUNNING.toString());
        resultDTO.setResult(true);
        resultDTO.setMessage(String.join(jobName,"start job "," successfully"));
        return resultDTO;
    }

    @Override
    public CommonResultDTO stopJob(String jobName) {
        CommonResultDTO resultDTO=new CommonResultDTO();
        JobDTO job = getJobStatus(jobName);
        if(job==null){
            resultDTO.setResult(false);
            resultDTO.setMessage(String.join(jobName,"job "," is not existed!"));
            return resultDTO;
        }
        if(job.getStatus().equals(JobStatus.RUNNING.toString())){
            resultDTO.setResult(false);
            resultDTO.setMessage(String.join(jobName,"job "," is not running!"));
            return resultDTO;
        }
        jobStatus.put(jobName,JobStatus.STOP.toString());
        resultDTO.setResult(true);
        resultDTO.setMessage(String.join(jobName,"stop job "," successfully"));
        return resultDTO;
    }

    private JobConfig getJobConfig(File inputFile) {
        log.debug("file=={}",inputFile.getAbsolutePath());
        JSONReader reader;
        try {
            reader = new JSONReader(new FileReader(inputFile));
        } catch (FileNotFoundException e) {
            log.error(e.getMessage(),e);
            return null;
        }

//        FileReader fileReader=new FileReader(inputFile);
        log.debug("{}",reader);
        String jsonString = null;
        try {
            jsonString = new String(Files.readAllBytes(inputFile.toPath()));
        } catch (IOException e) {
            log.error(e.getMessage(),e);
        }
        JobConfig jobConfig = JSON.parseObject(jsonString, new TypeReference<JobConfig>() {
        });
        return jobConfig;
    }
}
