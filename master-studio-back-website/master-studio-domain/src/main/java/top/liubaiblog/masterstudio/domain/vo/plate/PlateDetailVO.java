package top.liubaiblog.masterstudio.domain.vo.plate;

import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

/**
 * @author 留白
 * @description
 */
@Data
@ApiModel(description = "板块详情响应数据")
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
    @ApiModelProperty("板块类型(0左对齐, 1居中, 2右对齐)")
    private Integer plateType;
    /**
     * 板块类型名
     */
    @ApiModelProperty("板块类型名")
    private String plateTypeName;
    /**
     * 绑定的栏目编号
     */
    @ApiModelProperty("绑定的栏目编号")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long bindNav;
    /**
     * 绑定的栏目名
     */
    @ApiModelProperty("绑定的栏目名")
    private String bindNavName;
    /**
     * 是否禁用
     */
    @ApiModelProperty("是否禁用")
    private Boolean disabled;
    /**
     * 是否发布
     */
    @ApiModelProperty("是否发布")
    private Boolean released;
    /**
     * 排序种子
     */
    @ApiModelProperty("排序种子")
    private Integer orderSeed;
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
