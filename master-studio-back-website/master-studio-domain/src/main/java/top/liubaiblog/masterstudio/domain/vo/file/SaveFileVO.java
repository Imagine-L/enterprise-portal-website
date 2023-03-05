package top.liubaiblog.masterstudio.domain.vo.file;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author 留白
 * @description
 */
@Data
@ApiModel(description = "保存的文件对象")
public class SaveFileVO implements Serializable {
    @ApiModelProperty("文件名")
    private String filename;
    @ApiModelProperty("网络路径")
    private String networkPath;
}
