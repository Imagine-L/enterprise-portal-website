package top.liubaiblog.masterstudio.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * @author 留白
 * @description
 */
@Data
@ApiModel(description = "页面对象")
public class PageVO<T> implements Serializable {
    @ApiModelProperty("总记录数")
    private Long totalRecords;
    @ApiModelProperty("总页数")
    private Long totalPages;
    @ApiModelProperty("该页的对象列表")
    private List<T> records;
}
