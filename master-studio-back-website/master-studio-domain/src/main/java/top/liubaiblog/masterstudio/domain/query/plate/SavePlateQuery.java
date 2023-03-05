package top.liubaiblog.masterstudio.domain.query.plate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * @author 留白
 * @description
 */
@Data
@ApiModel("保存板块请求参数")
@JsonIgnoreProperties("image")
public class SavePlateQuery implements Serializable {
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
    @Size(max= 300,message="编码长度不能超过-1")
    @ApiModelProperty("板块描述")
    @Length(max= 300,message="编码长度不能超过-1")
    private String description;
    /**
     * 板块图片
     */
    @ApiModelProperty("板块图片")
    @NotNull(message = "板块图片不能为空")
    private MultipartFile image;
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
    @Min(value = 0, message = "板块类型不符合要求")
    @Max(value = 2, message = "板块类型不符合要求")
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
    @NotNull(message = "[是否发布]不能为空")
    @ApiModelProperty("是否发布")
    private Boolean released;
    /**
     * 排序种子
     */
    @NotNull(message="[排序种子]不能为空")
    @ApiModelProperty("排序种子")
    private Integer orderSeed;
}
