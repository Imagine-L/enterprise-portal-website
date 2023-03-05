package top.liubaiblog.masterstudio.domain.query.article;

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

/**
 * @author 留白
 * @description
 */
@Data
@ApiModel(description = "保存文章请求参数")
public class SaveArticleQuery implements Serializable {
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
    @Size(max= 300,message="编码长度不能超过200")
    @ApiModelProperty("文章摘要")
    @Length(max= 300,message="编码长度不能超过200")
    private String summary;
    /**
     * 文章html格式正文
     */
    @NotBlank(message = "文章内容不能为空")
    @ApiModelProperty("文章html格式正文")
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
}
