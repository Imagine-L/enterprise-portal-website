package top.liubaiblog.masterstudio.domain.vo.article;

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
@ApiModel("文章详情响应数据")
@Data
public class ArticleDetailVO implements Serializable {
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
     * 文章html格式正文
     */
    @ApiModelProperty("文章html格式正文")
    private String htmlContent;
    /**
     * 文章发布的栏目编号
     */
    @ApiModelProperty("文章发布的栏目编号")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long navId;
    /**
     * 文章发布的栏目名
     */
    @ApiModelProperty("文章发布的栏目名")
    private String navName;
    /**
     * 排序种子
     */
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
    /**
     * 作者昵称
     */
    @ApiModelProperty("作者昵称")
    private String author;
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
