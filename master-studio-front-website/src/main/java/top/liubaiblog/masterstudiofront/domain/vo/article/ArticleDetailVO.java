package top.liubaiblog.masterstudiofront.domain.vo.article;

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
@ApiModel(description = "查询文章详情响应数据")
public class ArticleDetailVO implements Serializable {
    @ApiModelProperty("文章编号")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long aid;
    @ApiModelProperty("文章标题")
    private String title;
    @ApiModelProperty("作者")
    private String author;
    @ApiModelProperty("作者岗位名")
    private String professionName;
    @ApiModelProperty("创建时间")
    private Date createTime;
    @ApiModelProperty("文章html正文内容")
    private String htmlContent;

    private static final long serialVersionUID = 1L;
}
