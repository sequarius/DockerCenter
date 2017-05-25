package gov.sequarius.dockercenter.center.domain;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.util.List;

/**
 * Created by Sequarius on 2017/4/17.
 */
@Data
public class Step {
    private Condition condition;
    @JSONField(name = "command_param")
    private List<String> commandParam;
}
