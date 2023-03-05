package top.liubaiblog.masterstudio.domain.vo.file;

import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * @author 留白
 * @description
 */
@ApiModel(description = "文件列表响应参数")
@Data
public class FilePartVO implements Serializable {
    /**
     * 文件编号
     */
    @ApiModelProperty("文件编号")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long fid;
    /**
     * 文件名
     */
    @ApiModelProperty("文件名")
    private String filename;
    /**
     * 原始文件名
     */
    @ApiModelProperty("原始文件名")
    private String originFilename;
    /**
     * 网络路径
     */
    @ApiModelProperty("网络路径")
    private String networkPath;
    /**
     * 本地路径
     */
    @ApiModelProperty("本地路径")
    private String localPath;
    /**
     * 文件大小
     */
    @ApiModelProperty("文件大小")
    private String size;
}
