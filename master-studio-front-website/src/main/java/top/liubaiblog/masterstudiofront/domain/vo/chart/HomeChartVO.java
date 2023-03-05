package top.liubaiblog.masterstudiofront.domain.vo.chart;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author 留白
 * @description
 */
@Data
@ApiModel(description = "轮播图响应数据")
public class HomeChartVO implements Serializable {
    /**
     * 轮播图编号
     */
    @ApiModelProperty("轮播编号")
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

    private static final long serialVersionUID = 1L;
}
