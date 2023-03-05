package top.liubaiblog.masterstudio.domain.query.file;

import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author 留白
 * @description
 */
@ApiModel(description = "获取文章列表列表请求参数")
@Data
public class GetFileQuery implements Serializable {
    /**
     * 系统文件名
     */
    @ApiModelProperty("系统文件名")
    private String filename;
    /**
     * 原始文件名
     */
    @ApiModelProperty("原始文件名")
    private String originFilename;
    /**
     * 开始时间
     */
    @ApiModelProperty("创建开始时间")
    private String createStartTime;
    /**
     * 结束时间
     */
    @ApiModelProperty("创建结束时间")
    private String createStopTime;

    @ApiModelProperty("当前页码")
    @Min(value = 1, message = "查询页码不能小于1")
    private Integer pageNo;

    @ApiModelProperty("查询记录数")
    @Max(value = 100, message = "查询记录数过多")
    @Min(value = 1, message = "查询记录数过少")
    private Integer pageSize;
}
