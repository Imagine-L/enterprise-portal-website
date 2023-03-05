package top.liubaiblog.masterstudio.domain.query.user;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * @author 留白
 * @description
 */
@Data
@ApiModel(description = "用户更新参数")
public class UpdateUserQuery implements Serializable {
    /**
     * 用户编号
     */
    @NotNull
    @ApiModelProperty("用户编号")
    private Long uid;
    /**
     * 昵称
     */
    @Size(max = 30, message = "编码长度不能超过30")
    @NotBlank
    @ApiModelProperty("昵称")
    @Length(max = 30, message = "编码长度不能超过30")
    private String nickname;
    /**
     * 性别(0男, 1女)
     */
    @Max(value = 1, message = "性别代码不符合要求")
    @Min(value = 0, message = "性别代码不符合要求")
    @ApiModelProperty("性别(0男, 1女)")
    private String gender;
    /**
     * 年龄
     */
    @ApiModelProperty("年龄")
    private Integer age;
    /**
     * 邮箱
     */
    @Email
    @Size(max = 30, message = "编码长度不能超过30")
    @ApiModelProperty("邮箱")
    @Length(max = 30, message = "编码长度不能超过30")
    private String email;
    /**
     * 手机号
     */
    @Size(max = 11, message = "编码长度不能超过11")
    @ApiModelProperty("手机号")
    @Length(max = 11, message = "编码长度不能超过11")
    private String phone;
    /**
     * qq号
     */
    @Size(max = 15, message = "编码长度不能超过15")
    @ApiModelProperty("qq号")
    @Length(max = 15, message = "编码长度不能超过15")
    private String qqNumber;
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
     * 是否锁定
     */
    @NotNull(message = "[是否锁定]不能为空")
    @ApiModelProperty("是否锁定")
    private Boolean locked;
}
