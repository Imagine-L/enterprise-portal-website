package top.liubaiblog.masterstudio.domain.vo.user;

import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

/**
 * @author 留白
 * @description 用户详细信息
 */
@Data
@ApiModel(description = "用户详情响应数据")
public class UserDetailVO implements Serializable {
    /**
     * 用户编号
     */
    @ApiModelProperty("用户编号")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long uid;
    /**
     * 用户名
     */
    @ApiModelProperty("用户名")
    private String username;
    /**
     * 昵称
     */
    @ApiModelProperty("昵称")
    private String nickname;
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
    @ApiModelProperty("qq号")
    private String qqNumber;
    /**
     * 邮箱
     */
    @ApiModelProperty("邮箱")
    private String email;
    /**
     * 是否为管理员
     */
    @ApiModelProperty("是否为管理员")
    private Boolean admin;
    /**
     * 描述
     */
    @ApiModelProperty("描述")
    private String description;
    /**
     * 用户岗位编号
     */
    @ApiModelProperty("用户岗位编号")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long professionId;
    /**
     * 用户岗位名
     */
    @ApiModelProperty("用户岗位名")
    private String professionName;
    /**
     * 是否允许删除
     */
    @ApiModelProperty("是否允许删除")
    private Boolean allowDel;
    /**
     * 是否锁定
     */
    @ApiModelProperty("是否锁定")
    private Boolean locked;
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
