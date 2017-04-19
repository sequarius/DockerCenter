package gov.sequarius.dockercenter.center.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONReader;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.serializer.SerializerFeature;
import gov.sequarius.dockercenter.center.domain.Condition;
import gov.sequarius.dockercenter.center.domain.JobConfig;
import gov.sequarius.dockercenter.center.domain.Step;
import gov.sequarius.dockercenter.center.service.JobService;
import gov.sequarius.dockercenter.common.rpc.CommonResultDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sequarius on 2017/4/7.
 */
@Service
@Slf4j
public class JobServiceImpl implements JobService {
    @Value("${gov.sequarius.docker.center.job-config-path}")
    private String JOB_CONFIG_PATH;


    private File JOB_CONFIG_DIR;

    @PostConstruct
    private void init() {
        JOB_CONFIG_DIR = new File(JOB_CONFIG_PATH);
        if (!JOB_CONFIG_DIR.exists()) {
            JOB_CONFIG_DIR.mkdir();
        }
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
        resultDTO.setMessage(String.format("config file %s create successfully",outputFile.getAbsolutePath()));
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

    private JobConfig getJobConfig(File inputFile) {
        JSONReader reader = null;
        try {
            reader = new JSONReader(new FileReader(inputFile));
        } catch (FileNotFoundException e) {
            log.error(e.getMessage(),e);
            return null;
        }
        String jsonString = reader.readString();
        JobConfig jobConfig = JSON.parseObject(jsonString, new TypeReference<JobConfig>() {
        });
        return jobConfig;
    }
}
