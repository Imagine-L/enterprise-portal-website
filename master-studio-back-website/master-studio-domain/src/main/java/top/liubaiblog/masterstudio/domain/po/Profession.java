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
 * 岗位表
 *
 * @TableName ms_profession
 */
@TableName("ms_profession")
@Data
@ApiModel(description = "岗位表")
public class Profession implements Serializable {
    /**
     * 岗位编号
     */
    @NotNull(message = "[岗位编号]不能为空")
    @ApiModelProperty("岗位编号")
    @TableId
    @JsonSerialize(using = ToStringSerializer.class)
    private Long pid;
    /**
     * 岗位名称
     */
    @NotBlank(message = "[岗位名称]不能为空")
    @Size(max = 20, message = "编码长度不能超过20")
    @ApiModelProperty("岗位名称")
    @Length(max = 20, message = "编码长度不能超过20")
    private String name;
    /**
     * 岗位描述
     */
    @Size(max = 100, message = "编码长度不能超过100")
    @ApiModelProperty("岗位描述")
    @Length(max = 100, message = "编码长度不能超过100")
    private String description;
    /**
     * 是否允许删除
     */
    @NotNull(message = "[是否允许删除]不能为空")
    @ApiModelProperty("是否允许删除")
    private Boolean allowDel;
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
