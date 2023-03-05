package top.liubaiblog.masterstudio.domain.query.log;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.Date;

/**
 * @author 留白
 * @description
 */
@ApiModel(description = "获取日志列表列表请求参数")
@Data
public class GetLogQuery {
    /**
     * 操作的模块
     */
    @ApiModelProperty("操作的模块")
    private String module;
    /**
     * 操作类型
     */
    @ApiModelProperty("操作类型")
    private String operType;
    /**
     * 请求方式
     */
    @ApiModelProperty("请求方式")
    private String requestMode;
    /**
     * 请求用户名
     */
    @ApiModelProperty("请求用户名")
    private String requestUser;
    /**
     * 创建时间[开始]
     */
    @ApiModelProperty("创建时间")
    private String createStartTime;
    /**
     * 创建时间[结束]
     */
    @ApiModelProperty("创建时间")
    private String createStopTime;

    @ApiModelProperty("当前页码")
    @Min(value = 1, message = "查询页码不能小于1")
    private Integer pageNo;

    @ApiModelProperty("查询记录数")
    @Max(value = 100, message = "查询记录数过多")
    @Min(value = 1, message = "查询记录数过少")
    private Integer pageSize;
}
