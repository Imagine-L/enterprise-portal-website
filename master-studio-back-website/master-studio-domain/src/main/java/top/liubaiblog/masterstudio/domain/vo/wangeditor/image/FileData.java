package top.liubaiblog.masterstudio.domain.vo.wangeditor.image;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author 留白
 * @description
 */
@Data
@ApiModel(description = "保存图片响应数据")
public class FileData implements Serializable {
    @ApiModelProperty("图片 src ，必须")
    private String url;
    @ApiModelProperty("图片描述文字，非必须")
    private String alt;
    @ApiModelProperty("图片的链接，非必须")
    private String href;
}
