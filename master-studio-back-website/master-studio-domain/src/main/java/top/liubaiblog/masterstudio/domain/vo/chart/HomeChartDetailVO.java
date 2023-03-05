package top.liubaiblog.masterstudio.domain.vo.chart;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 留白
 * @description
 */
@Data
@ApiModel(description = "轮播图详情")
public class HomeChartDetailVO implements Serializable {
    /**
     * 轮播图编号
     */
    @ApiModelProperty("轮播图编号")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long hid;
    /**
     * 轮播图片
     */
    @ApiModelProperty("轮播图片")
    private String image;
    /**
     * 轮播图链接
     */
    @ApiModelProperty("轮播图链接")
    private String link;
    /**
     * 创建者
     */
    @ApiModelProperty("创建者")
    private String createBy;
    /**
     * 创建时间
     */
    @ApiModelProperty("创建时间")
    private Date createTime;
    /**
     * 更新者
     */
    @ApiModelProperty("更新者")
    private String updateBy;
    /**
     * 更新时间
     */
    @ApiModelProperty("更新时间")
    private Date updateTime;
}
