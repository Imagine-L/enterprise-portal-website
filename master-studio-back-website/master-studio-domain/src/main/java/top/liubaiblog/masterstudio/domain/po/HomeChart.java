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
* 栏目表
* @TableName ms_home_chart
*/
@TableName("ms_home_chart")
@Data
@ApiModel(description = "栏目表")
public class HomeChart implements Serializable {

    /**
    * 轮播图编号
    */
    @NotNull(message="[轮播图编号]不能为空")
    @ApiModelProperty("轮播图编号")
    @TableId
    @JsonSerialize(using = ToStringSerializer.class)
    private Long hid;
    /**
    * 轮播图片
    */
    @NotBlank(message="[轮播图片]不能为空")
    @Size(max= 200,message="编码长度不能超过200")
    @ApiModelProperty("轮播图片")
    @Length(max= 200,message="编码长度不能超过200")
    private String image;
    /**
    * 轮播图链接
    */
    @Size(max= 300,message="编码长度不能超过300")
    @ApiModelProperty("轮播图链接")
    @Length(max= 300,message="编码长度不能超过300")
    private String link;
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
