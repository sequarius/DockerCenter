package gov.sequarius.dockercenter.center.domain;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.math.BigDecimal;

/**
 * Created by Sequarius on 2017/4/17.
 */
@Data
public class Condition {
    private Long delay;
    @JSONField(name = "memory_avg_rate_below")
    private BigDecimal memoryAvgRateBelow;
    @JSONField(name="memory_avg_rate_above")
    private BigDecimal memoryAvgRateAbove;
    @JSONField(name="cpu_avg_rate_above")
    private BigDecimal cpuAvgRateAbove;
    @JSONField(name = "cpu_avg_rate_below")
    private BigDecimal cpuAvgRateBelow;
}
