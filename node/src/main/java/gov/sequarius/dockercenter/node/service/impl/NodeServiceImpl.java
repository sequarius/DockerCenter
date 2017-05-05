package gov.sequarius.dockercenter.node.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONReader;
import com.github.javafaker.Faker;
import com.sun.management.OperatingSystemMXBean;
import gov.sequarius.dockercenter.common.rpc.*;
import gov.sequarius.dockercenter.node.service.CommandService;
import gov.sequarius.dockercenter.node.service.NodeService;
import gov.sequarius.dockercenter.node.util.GrepUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.thrift.TException;
import org.apache.thrift.transport.TSocket;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.*;
import java.lang.management.ManagementFactory;
import java.util.Map;
import java.util.Properties;

/**
 * Created by Sequarius on 2017/3/26.
 */
@Service
@Slf4j
public class NodeServiceImpl implements NodeService {
    public static final String NODE_NAME = "node_name";
    @Resource
    CenterSynRPCService.Client centerSynClient;

    @Resource
    TSocket tSocket;

    private NodeInfoDTO nodeInfoDTO;

    @Resource
    CommandService commandService;

    @Value("${gov.sequarius.docker_center.file_path}")
    private String OUTPUT_FILE_DIR_PATH;

    private File OUTPUT_FILE_DIR;

    @Resource
    private Faker faker;

    @PostConstruct
    private void init(){
        nodeInfoDTO = new NodeInfoDTO();
        Properties props = System.getProperties();
        String osName = props.getProperty("os.name");
        String osVersion = props.getProperty("os.version");
        String osArch = props.getProperty("os.arch");
        nodeInfoDTO.setArchitecture(String.join("/", osName, osVersion, osArch));
        OUTPUT_FILE_DIR = new File(OUTPUT_FILE_DIR_PATH);
        if (!OUTPUT_FILE_DIR.exists()) {
            OUTPUT_FILE_DIR.mkdir();
        }
        log.debug(OUTPUT_FILE_DIR_PATH);
        File file=new File(OUTPUT_FILE_DIR,"config.json");
        String nodeName;
        if(!file.exists()){
            nodeName = faker.name().fullName();
            JSONObject obj=new JSONObject();
            obj.put(NODE_NAME,nodeName);
            if (!file.exists()) {
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    log.error(e.getMessage(),e);
                }
            }
            try (FileOutputStream outputStream = new FileOutputStream(file)){

                outputStream.write(obj.toJSONString().getBytes());
            } catch (FileNotFoundException e) {
                log.error(e.getMessage(),e);
            } catch (IOException e) {
                log.error(e.getMessage(),e);
            }
        }else {
            JSONReader reader = null;
            try {
                reader = new JSONReader(new FileReader(file));
            } catch (FileNotFoundException e) {
                log.error(e.getMessage(), e);
            }
            String jsonString = reader.readString();
            JSONObject jsonObject = JSON.parseObject(jsonString);
            nodeName=jsonObject.getString(NODE_NAME);
        }
        nodeInfoDTO.setName(nodeName);
        try {
            tSocket.open();
            CommonResultDTO commonResultDTO = centerSynClient.registerNode(nodeInfoDTO, "544484");
            log.info("register node {}",commonResultDTO);
        } catch (TException e) {
            log.warn(e.getMessage(),e);
        }finally {
            tSocket.close();
        }
    }

    public Boolean updateNodeInfo() {
        OperatingSystemMXBean systemMXBean = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
        CommandDTO commandDTO=new CommandDTO();
        commandDTO.setCommand("info");

        ExecuteResultDTO resultDTO = commandService.executeCommandOnNode(commandDTO);
        if(resultDTO.getResultCode()==0) {
            Map<String, String> infoMap = GrepUtil.grepDockerInfo(resultDTO.getReturnMessage());
            nodeInfoDTO.setDockerVersion(infoMap.get("Server Version"));
            nodeInfoDTO.setContainerCount(Long.valueOf(infoMap.get("Containers")));
            nodeInfoDTO.setRunningContainerCount(Long.valueOf(infoMap.get("Running")));
        }else{
            nodeInfoDTO.setDockerVersion("UNKNOWN");
        }
        nodeInfoDTO.setFreeDiskSpace(systemMXBean.getFreeSwapSpaceSize());
        nodeInfoDTO.setFreeMemorySpace(systemMXBean.getFreePhysicalMemorySize());
        nodeInfoDTO.setCallTime(System.currentTimeMillis());
        try {
            tSocket.open();
            CommonResultDTO commonResultDTO = centerSynClient.updateNodeInfo(nodeInfoDTO);
            if (commonResultDTO.isResult()) {
                return true;
            } else {
                log.warn("cant update node_info {},message=={}", nodeInfoDTO, commonResultDTO);
                return false;
            }
        } catch (TException e) {
            log.warn(e.getMessage(), e);
            return false;
        }finally {
            tSocket.close();
        }
    }
}
