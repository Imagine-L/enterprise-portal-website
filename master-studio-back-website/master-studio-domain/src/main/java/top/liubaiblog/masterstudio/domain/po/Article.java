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
* 文章表
* @TableName ms_article
*/
@Data
@TableName("ms_article")
@ApiModel(description = "文章表")
public class Article implements Serializable {
    /**
    * 文章编号
    */
    @NotNull(message="[文章编号]不能为空")
    @ApiModelProperty("文章编号")
    @TableId
    @JsonSerialize(using = ToStringSerializer.class)
    private Long aid;
    /**
    * 文章标题
    */
    @Size(max= 20,message="编码长度不能超过20")
    @ApiModelProperty("文章标题")
    @Length(max= 20,message="编码长度不能超过20")
    private String title;
    /**
    * 文章摘要
    */
    @Size(max= 200,message="编码长度不能超过200")
    @ApiModelProperty("文章摘要")
    @Length(max= 200,message="编码长度不能超过200")
    private String summary;
    /**
    * 文章html格式正文
    */
    @Size(max= -1,message="编码长度不能超过-1")
    @ApiModelProperty("文章html格式正文")
    @Length(max= -1,message="编码长度不能超过-1")
    private String htmlContent;
    /**
    * 文章发布的栏目
    */
    @NotNull(message="[文章发布的栏目]不能为空")
    @ApiModelProperty("文章发布的栏目")
    private Long navId;
    /**
    * 排序种子
    */
    @NotNull(message="[排序种子]不能为空")
    @ApiModelProperty("排序种子")
    private Integer orderSeed;
    /**
     * 是否发布
     */
    @ApiModelProperty("是否发布")
    private Boolean released;
    /**
     * 是否禁用
     */
    @ApiModelProperty("是否禁用")
    private Boolean disabled;
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
