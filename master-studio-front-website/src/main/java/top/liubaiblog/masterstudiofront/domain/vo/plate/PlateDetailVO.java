package top.liubaiblog.masterstudiofront.domain.vo.plate;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
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
@ApiModel(description = "查询页面板块响应数据")
public class PlateDetailVO implements Serializable {
    /**
     * 板块编号
     */
    @ApiModelProperty("板块编号")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long pid;
    /**
     * 板块标题
     */
    @ApiModelProperty("板块标题")
    private String plateName;

    /**
     * 板块描述
     */
    @ApiModelProperty("板块描述")
    private String description;

    /**
     * 板块图片
     */
    @ApiModelProperty("板块图片")
    private String image;

    /**
     * 板块链接
     */
    @ApiModelProperty("板块链接")
    private String link;

    /**
     * 板块类型(0左对齐, 1居中, 2右对齐)
     */
    private Integer plateType;

    private static final long serialVersionUID = 1L;
}
