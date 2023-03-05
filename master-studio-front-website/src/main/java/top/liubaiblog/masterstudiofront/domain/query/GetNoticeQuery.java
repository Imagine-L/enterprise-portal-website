package top.liubaiblog.masterstudiofront.domain.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 留白
 * @description
 */
@Data
@ApiModel(description = "获取通知列表请求参数")
public class GetNoticeQuery {
    @ApiModelProperty("日期")
    private String date;
    @ApiModelProperty("通知标题")
    private String title;
    @ApiModelProperty("当前页码")
    private Integer pageNo;
    @ApiModelProperty("查询记录数")
    private Integer pageSize;
}
