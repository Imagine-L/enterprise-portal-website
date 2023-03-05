package top.liubaiblog.masterstudio.domain.query.navigation;

import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * @author 留白
 * @description
 */
@Data
@ApiModel(description = "保存栏目请求参数")
@JsonIgnoreProperties("image")
public class SaveNavigationQuery implements Serializable {
    /**
     * 栏目名
     */
    @NotBlank(message="[栏目名]不能为空")
    @Size(max= 10,message="编码长度不能超过10")
    @ApiModelProperty("栏目名")
    @Length(max= 10,message="编码长度不能超过10")
    private String navName;
    /**
     * 栏目级别
     */
    @NotNull(message="[栏目级别]不能为空")
    @ApiModelProperty("栏目级别")
    @Min(value = 1, message = "栏目级别不符合需求")
    @Max(value = 2, message = "栏目级别不符合需求")
    private Integer level;
    /**
     * 父栏目编号
     */
    @ApiModelProperty("父栏目编号")
    private Long parentId;
    /**
     * 栏目图片路径
     */
    @NotNull
    @ApiModelProperty("栏目图片路径")
    private MultipartFile image;
    /**
     * 栏目描述
     */
    @Size(max= 100,message="编码长度不能超过-1")
    @ApiModelProperty("栏目描述")
    @Length(max= 100,message="编码长度不能超过-1")
    private String description;
    /**
     * 是否禁用
     */
    @NotNull(message="[是否禁用]不能为空")
    @ApiModelProperty("是否禁用")
    private Boolean disabled;
    /**
     * 栏目类型(0父栏目, 1页面, 2文章)
     */
    @NotNull(message="[栏目类型(0父栏目, 1页面, 2文章)]不能为空")
    @ApiModelProperty("栏目类型(0父栏目, 1页面, 2文章)")
    @Min(value = 0, message = "栏目类型不符合需求")
    @Max(value = 2, message = "栏目类型不符合需求")
    private Integer navType;
    /**
     * 访问路径
     */
    @NotBlank(message = "访问路径不能为空")
    @ApiModelProperty("访问路径")
    @Pattern(regexp = "^[a-z_\\-0-9]{1,10}$", message = "访问路径不符合规则")
    @Size(min = 1, max = 10, message = "访问路径长度不合法")
    private String path;
    /**
     * 排序种子
     */
    @NotNull(message="[排序种子]不能为空")
    @ApiModelProperty("排序种子")
    private Integer orderSeed;
}
