package top.liubaiblog.masterstudio.domain.query.chart;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * @author 留白
 * @description
 */
@Data
@ApiModel(description = "保存轮播图请求参数")
@JsonIgnoreProperties("image")
public class SaveHomeChartQuery implements Serializable {
    /**
     * 轮播图片
     */
    @NotNull(message = "轮播图片不能为空")
    @ApiModelProperty("轮播图片")
    private MultipartFile image;
    /**
     * 轮播图链接
     */
    @Size(max= 300,message="编码长度不能超过300")
    @ApiModelProperty("轮播图链接")
    @Length(max= 300,message="编码长度不能超过300")
    private String link;
}
