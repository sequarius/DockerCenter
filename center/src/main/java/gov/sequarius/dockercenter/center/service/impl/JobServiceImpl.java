package gov.sequarius.dockercenter.center.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONReader;
import com.alibaba.fastjson.serializer.SerializerFeature;
import gov.sequarius.dockercenter.center.service.JobService;
import gov.sequarius.dockercenter.common.rpc.CommonResultDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.*;

/**
 * Created by Sequarius on 2017/4/7.
 */
@Service
@Slf4j
public class JobServiceImpl implements JobService{
    @Value("${gov.sequarius.docker.center.job-config-path}")
    private String JOB_CONFIG_PATH;


    private File JOB_CONFIG_DIR;

    @PostConstruct
    private void init(){
        JOB_CONFIG_DIR=new File(JOB_CONFIG_PATH);
        if(!JOB_CONFIG_DIR.exists()){
            JOB_CONFIG_DIR.mkdir();
        }
    }
    @Override
    public CommonResultDTO createJob(String jobName) {
        Resource resource = new ClassPathResource("/template/job_config.json");
        try {
            File inputFile = resource.getFile();
            JSONReader reader = new JSONReader(new FileReader(inputFile));
            String jsonString = reader.readString();
            JSONObject obj=JSON.parseObject(jsonString);
            log.debug(jsonString);
            File outputFile=new File(JOB_CONFIG_DIR,jobName+".json");
            try (FileOutputStream outputStream = new FileOutputStream(outputFile)){
                outputStream.write(JSON.toJSONString(obj, SerializerFeature.PrettyFormat).getBytes());
            } catch (FileNotFoundException e) {
                log.error(e.getMessage(),e);
            } catch (IOException e) {
                log.error(e.getMessage(),e);
            }
            log.debug("file {} write finish",outputFile.getAbsolutePath());
        } catch (IOException e) {
            log.error("job_config.json load failed" + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
}
