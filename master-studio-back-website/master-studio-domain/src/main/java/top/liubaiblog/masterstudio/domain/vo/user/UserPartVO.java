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
 * @description 用于前台用户列表的部分用户数据
 */
@Data
@ApiModel(description = "查看用户列表响应数据")
public class UserPartVO implements Serializable {
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
     * 是否允许删除
     */
    @ApiModelProperty("是否允许删除")
    private Boolean allowDel;
    /**
     * 是否锁定
     */
    @ApiModelProperty("是否锁定")
    private Boolean locked;
}
