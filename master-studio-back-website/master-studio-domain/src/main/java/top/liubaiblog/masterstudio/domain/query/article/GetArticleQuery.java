package top.liubaiblog.masterstudio.domain.query.article;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

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
@ApiModel(description = "获取文章列表请求参数")
public class GetArticleQuery implements Serializable {
    /**
     * 文章标题
     */
    @ApiModelProperty("文章标题")
    private String title;
    /**
     * 创建者
     */
    @ApiModelProperty("创建者")
    private String author;
    /**
     * 文章发布的栏目
     */
    @ApiModelProperty("文章发布的栏目")
    private Long navId;

    @ApiModelProperty("当前页码")
    @Min(value = 1, message = "查询页码不能小于1")
    private Integer pageNo;

    @ApiModelProperty("查询记录数")
    @Max(value = 100, message = "查询记录数过多")
    @Min(value = 1, message = "查询记录数过少")
    private Integer pageSize;
}
