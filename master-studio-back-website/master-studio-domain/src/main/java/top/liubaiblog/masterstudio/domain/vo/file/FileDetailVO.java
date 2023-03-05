package top.liubaiblog.masterstudio.domain.vo.file;

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
@ApiModel(description = "文件表")
public class FileDetailVO implements Serializable {
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
     * 文件大小(MB)
     */
    @ApiModelProperty("文件大小")
    private String size;
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
