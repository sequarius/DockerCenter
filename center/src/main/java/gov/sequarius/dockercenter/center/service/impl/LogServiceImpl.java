package gov.sequarius.dockercenter.center.service.impl;

import gov.sequarius.dockercenter.center.service.LogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Sequarius on 2017/5/17.
 */
@Service
@Slf4j
public class LogServiceImpl implements LogService {
    private File LOG_SAVED_BASE_DIR;
    private File LOG_MERGER_BASE_DIR;
    private Long MAX_FILE_SIZE;
    private DateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd-hh-mm-ss");
    Map<String,Integer> lastLogFileName=new HashMap<>();
    @Override
    public Boolean mergerLog(Long startTime,String imageName) {
        File logDir=new File(LOG_SAVED_BASE_DIR,imageName);
        Date date=new Date(startTime);
        List<File> mergeList=new ArrayList<>();
        //to find the log should to merge;
        for (File file : logDir.listFiles()) {
            try {
                //to get the log date
                if(dateFormat.parse(file.getName().substring(0,20)).after(date)){
                    mergeList.add(file);
                }
            } catch (ParseException e) {
                log.error(e.getMessage(),e);
            }
        }
        return true;
    }

    private void mergeLog(List<File> logs,String imageNames){
        File lastLogFile=getLastLogFile(imageNames);
        if(lastLogFile.length()>MAX_FILE_SIZE){
            File file=new File(LOG_MERGER_BASE_DIR,imageNames+lastLogFileName.get(imageNames)+1);
        }
        File file
    }

    private File getLastLogFile(String imageNames) {
        Integer fileIndex = lastLogFileName.get(imageNames);
        File lastLogFile = null;
        while (fileIndex==null){
            Integer maxIndex=0;
            lastLogFile=new File(LOG_MERGER_BASE_DIR,imageNames+maxIndex);
            if(!lastLogFile.exists()){
                fileIndex=maxIndex;
                lastLogFileName.put(imageNames,fileIndex);
            }
        }
        return lastLogFile;
    }
}
