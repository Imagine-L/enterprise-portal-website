package top.liubaiblog.masterstudio.domain.po;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import javax.validation.constraints.NotNull;

import java.io.Serializable;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
 * 文件表
 *
 * @TableName ms_upload_file
 */
@TableName("ms_upload_file")
@Data
@ApiModel(description = "文件表")
public class UploadFile implements Serializable {
    /**
     * 文件编号
     */
    @NotNull(message = "[文件编号]不能为空")
    @ApiModelProperty("文件编号")
    @TableId
    @JsonSerialize(using = ToStringSerializer.class)
    private Long fid;
    /**
     * 文件名
     */
    @NotBlank(message = "[文件名]不能为空")
    @Size(max = 50, message = "编码长度不能超过50")
    @ApiModelProperty("文件名")
    @Length(max = 50, message = "编码长度不能超过50")
    private String filename;
    /**
     * 原始文件名
     */
    @Size(max = 50, message = "编码长度不能超过50")
    @ApiModelProperty("原始文件名")
    @Length(max = 50, message = "编码长度不能超过50")
    private String originFilename;
    /**
     * 网络路径
     */
    @NotBlank(message = "[网络路径]不能为空")
    @Size(max = 200, message = "编码长度不能超过200")
    @ApiModelProperty("网络路径")
    @Length(max = 200, message = "编码长度不能超过200")
    private String networkPath;
    /**
     * 本地路径
     */
    @NotBlank(message = "[本地路径]不能为空")
    @Size(max = 200, message = "编码长度不能超过200")
    @ApiModelProperty("本地路径")
    @Length(max = 200, message = "编码长度不能超过200")
    private String localPath;
    /**
     * 文件大小(MB)
     */
    @NotNull(message = "[文件大小(MB)]不能为空")
    @ApiModelProperty("文件大小(MB)")
    private Long size;
    /**
     * 创建者
     */
    @ApiModelProperty("创建者")
    private Long createBy;
    /**
     * 创建时间
     */
    @ApiModelProperty("创建时间")
    private Date createTime;
    /**
     * 更新者
     */
    @ApiModelProperty("更新者")
    private Long updateBy;
    /**
     * 更新时间
     */
    @ApiModelProperty("更新时间")
    private Date updateTime;
}
