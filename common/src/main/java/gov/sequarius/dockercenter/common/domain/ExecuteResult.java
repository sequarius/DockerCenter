package gov.sequarius.dockercenter.common.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

/**
 * Created by Sequarius on 2017/3/20.
 */
@Data
@Getter
@AllArgsConstructor
public class ExecuteResult {
    private int resultCode;
    private String returnMessage;
}
