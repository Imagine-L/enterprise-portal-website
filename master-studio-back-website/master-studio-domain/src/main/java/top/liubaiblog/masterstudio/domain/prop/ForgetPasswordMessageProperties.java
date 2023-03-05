package top.liubaiblog.masterstudio.domain.prop;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author 留白
 * @description
 */
@Data
@ConfigurationProperties(prefix = "forget")
public class ForgetPasswordMessageProperties {
    public static final String subject_1 = "大师工作室后台-找回密码验证";
    public static final String text_1 = "你的验证码是：";
    private String subject = "大师工作室后台-找回密码验证";
    private String type = "txt";
    private String text = "你的验证码是：${code}";
}
