package top.liubaiblog.masterstudio.domain.vo.article;

import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

/**
 * @author 留白
 * @description
 */
@Data
@ApiModel(description = "查看文章列表响应数据")
public class ArticlePartVO implements Serializable {
    /**
     * 文章编号
     */
    @ApiModelProperty("文章编号")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long aid;
    /**
     * 文章标题
     */
    @ApiModelProperty("文章标题")
    private String title;
    /**
     * 文章摘要
     */
    @ApiModelProperty("文章摘要")
    private String summary;
    /**
     * 文章发布的栏目编号
     */
    @ApiModelProperty("文章发布的栏目编号")
    private String navName;
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
    /**
     * 作者昵称
     */
    @ApiModelProperty("作者昵称")
    private String author;
}
