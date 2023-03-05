package top.liubaiblog.masterstudio.domain.po;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import javax.validation.constraints.NotNull;

import java.io.Serializable;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
* 页面板块表
* @TableName ms_page_plate
*/
@TableName("ms_page_plate")
@Data
@ApiModel(description = "页面板块表")
public class PagePlate implements Serializable {
    /**
    * 板块编号
    */
    @NotNull(message="[板块编号]不能为空")
    @ApiModelProperty("板块编号")
    @TableId
    @JsonSerialize(using = ToStringSerializer.class)
    private Long pid;
    /**
    * 板块标题
    */
    @Size(max= 20,message="编码长度不能超过20")
    @ApiModelProperty("板块标题")
    @Length(max= 20,message="编码长度不能超过20")
    private String plateName;
    /**
    * 板块描述
    */
    @Size(max= -1,message="编码长度不能超过-1")
    @ApiModelProperty("板块描述")
    @Length(max= -1,message="编码长度不能超过-1")
    private String description;
    /**
    * 板块图片
    */
    @Size(max= 200,message="编码长度不能超过200")
    @ApiModelProperty("板块图片")
    @Length(max= 200,message="编码长度不能超过200")
    private String image;
    /**
    * 板块链接
    */
    @Size(max= 300,message="编码长度不能超过300")
    @ApiModelProperty("板块链接")
    @Length(max= 300,message="编码长度不能超过300")
    private String link;
    /**
    * 板块类型(0左对齐, 1居中, 2右对齐)
    */
    @NotNull(message="[板块类型(0左对齐, 1居中, 2右对齐)]不能为空")
    @ApiModelProperty("板块类型(0左对齐, 1居中, 2右对齐)")
    private Integer plateType;
    /**
    * 绑定的栏目编号
    */
    @NotNull(message="[绑定的栏目编号]不能为空")
    @ApiModelProperty("绑定的栏目编号")
    private Long bindNav;
    /**
    * 是否禁用
    */
    @NotNull(message="[是否禁用]不能为空")
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
    @NotNull(message="[排序种子]不能为空")
    @ApiModelProperty("排序种子")
    private Integer orderSeed;
    /**
    * 创建者
    */
    @ApiModelProperty("创建者")
    private Long createBy;
    /**
    * 创建时间
    */
    @ApiModelProperty("创建时间")
    private Date createTime;
    /**
    * 更新者
    */
    @ApiModelProperty("更新者")
    private Long updateBy;
    /**
    * 更新时间
    */
    @ApiModelProperty("更新时间")
    private Date updateTime;
}
