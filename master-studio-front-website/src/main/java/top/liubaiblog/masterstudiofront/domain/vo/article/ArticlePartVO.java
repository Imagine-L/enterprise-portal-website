package top.liubaiblog.masterstudiofront.domain.vo.article;

import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 留白
 * @description
 */
@Data
@ApiModel("查询文章列表响应数据")
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
     * 创建时间
     */
    @ApiModelProperty("创建时间")
    private Date createTime;

    private static final long serialVersionUID = 1L;
}
