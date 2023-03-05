package top.liubaiblog.masterstudio.domain.po;

import javax.validation.constraints.Email;
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
 * 用户表
 *
 * @TableName ms_user
 */
@TableName("ms_user")
@Data
@ApiModel(description = "用户表")
public class User implements Serializable {
    /**
     * 用户编号
     */
    @NotNull(message = "[用户编号]不能为空")
    @ApiModelProperty("用户编号")
    @TableId
    @JsonSerialize(using = ToStringSerializer.class)
    private Long uid;
    /**
     * 用户名
     */
    @NotBlank(message = "[用户名]不能为空")
    @Size(max = 20, message = "编码长度不能超过20")
    @ApiModelProperty("用户名")
    @Length(max = 20, message = "编码长度不能超过20")
    private String username;
    /**
     * 昵称
     */
    @Size(max = 30, message = "编码长度不能超过30")
    @ApiModelProperty("昵称")
    @Length(max = 30, message = "编码长度不能超过30")
    private String nickname;
    /**
     * 密码
     */
    @NotNull(message = "[密码]不能为空")
    @ApiModelProperty("密码")
    private String password;
    /**
     * 性别(0男, 1女)
     */
    @ApiModelProperty("性别(0男, 1女)")
    private String gender;
    /**
     * 年龄
     */
    @ApiModelProperty("年龄")
    private Integer age;
    /**
     * 手机号
     */
    @ApiModelProperty("手机号")
    private String phone;
    /**
     * qq号
     */
    @Size(max = 15, message = "编码长度不能超过15")
    @ApiModelProperty("qq号")
    @Length(max = 15, message = "编码长度不能超过15")
    private String qqNumber;
    /**
     * 邮箱
     */
    @Email
    @Size(max = 30, message = "编码长度不能超过30")
    @ApiModelProperty("邮箱")
    @Length(max = 30, message = "编码长度不能超过30")
    private String email;
    /**
     * 是否为管理员
     */
    @NotNull(message = "[是否为管理员]不能为空")
    @ApiModelProperty("是否为管理员")
    private Boolean admin;
    /**
     * 描述
     */
    @Size(max = 100, message = "编码长度不能超过100")
    @ApiModelProperty("描述")
    @Length(max = 100, message = "编码长度不能超过100")
    private String description;
    /**
     * 用户岗位编号
     */
    @ApiModelProperty("用户岗位编号")
    private Long professionId;
    /**
     * 是否允许删除
     */
    @NotNull(message = "[是否允许删除]不能为空")
    @ApiModelProperty("是否允许删除")
    private Boolean allowDel;
    /**
     * 是否锁定
     */
    @NotNull(message = "[是否锁定]不能为空")
    @ApiModelProperty("是否锁定")
    private Boolean locked;
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
