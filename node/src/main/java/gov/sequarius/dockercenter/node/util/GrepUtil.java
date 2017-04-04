package gov.sequarius.dockercenter.node.util;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Sequarius on 2017/3/29.
 */
public class GrepUtil {

    public static final String UN_GREPED_STRING = "UNKNOWN";

    public static String grepDockerVersion(String input){
        String[] lines=input.split("\n");
        for (int i = 0; i < lines.length; i++) {
            String line=lines[i];
            if(!line.contains("Server:")){
                continue;
            }
            line=lines[i+1];
            if(line.contains("Version:")){
                return line.replace("Version:","").trim();
            }
        }
        return UN_GREPED_STRING;
    }
    
    public static Map<String,String> grepDockerInfo(String input){
        Map<String,String> map=new HashMap<>(20);
        String[] lines=input.split("\n");
        for (String line : lines) {
            String[] split = line.split(":");
            if(split.length!=2){
                continue;
            }
            map.put(split[0].trim(),split[1].trim());
        }
        return map;
    }

}
