package gov.sequarius.dockercenter.center.domain;

import gov.sequarius.dockercenter.common.rpc.NodeInfoDTO;
import lombok.Data;

import java.util.List;

/**
 * Created by Sequarius on 2017/5/7.
 */
@Data
public class NodeInfo extends NodeInfoDTO {
    private Long load;
    private List<String> containerName;
    private boolean available;
}
