package top.liubaiblog.masterstudio.domain.message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * @author 留白
 * @description
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailMessage {
    /**
     * 接收人
     */
    @NotBlank
    @Size(max = 50, message = "编码长度不能超过50")
    private String sendTo;

    /**
     *  邮件主题
     */
    @NotBlank
    @Size(max = 50, message = "编码长度不能超过50")
    private String subject;

    /**
     *  邮件内容
     */
    @NotBlank
    private String text;

    /**
     *  附件路径
     */
    private String filePath;

    /**
     * 类型(html或者txt)
     */
    @NotBlank
    @Pattern(regexp = "(html)|(txt)")
    private String type;
}
