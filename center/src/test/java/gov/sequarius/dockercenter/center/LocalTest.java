package gov.sequarius.dockercenter.center;

import gov.sequarius.dockercenter.common.rpc.NodeInfoDTO;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Sequarius on 2017/3/20.
 */
@Slf4j
public class LocalTest {
    @Test
    public void testEqual(){
        NodeInfoDTO nodeInfoDTO=new NodeInfoDTO();
        nodeInfoDTO.setName("heihei");
        Map<String,NodeInfoDTO> strMap=new HashMap<>();
        Map<Integer,NodeInfoDTO> intMap =new HashMap<>();

        strMap.put("haha",nodeInfoDTO);
        intMap.put(1,nodeInfoDTO);

        nodeInfoDTO=strMap.get("haha");
        nodeInfoDTO.setName("zhangsan");

        log.debug("node info get from int map=={}",intMap.get(1));

    }
}
