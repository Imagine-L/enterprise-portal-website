package top.liubaiblog.masterstudio.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author 留白
 * @description
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "检查是否重复响应数据")
public class CheckRepeatVO implements Serializable {
    @ApiModelProperty("是否重复")
    private Boolean repeat;
}
