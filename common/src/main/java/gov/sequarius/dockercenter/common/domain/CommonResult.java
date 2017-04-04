package gov.sequarius.dockercenter.common.domain;

import gov.sequarius.dockercenter.common.constant.ResultCode;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Created by Sequarius on 2017/3/20.
 */
@Data
@AllArgsConstructor
public class CommonResult {
    private Integer code;
    private Boolean result;
    private String message;

    public static CommonResult result(boolean result,String message,Integer code){
        return new CommonResult(code,result,message);
    }

    public static CommonResult result(boolean result,String message){
        return result(result,message, ResultCode.DEFAULT_CODE);
    }


}
