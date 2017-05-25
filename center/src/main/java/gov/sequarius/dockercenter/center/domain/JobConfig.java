package gov.sequarius.dockercenter.center.domain;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * Created by Sequarius on 2017/4/17.
 */
@Data
public class JobConfig {
    private String name;
    private Long id;
    @JSONField(name = "deploy_strategy")
    private String deployStrategy;
    @JSONField(name = "sub_name_strategy")
    private String subNameStrategy;
    @JSONField(name = "image_name")
    private String imageName;
    @JSONField(name = "start_step")
    private Step startStep;
    @JSONField(name = "end_step")
    private Step endStep;
}
