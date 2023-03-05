package top.liubaiblog.masterstudio.domain.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 留白
 * @description 忘记密码获取的验证码
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CodeBO {
    // 用户编号
    private Long uid;
    // 验证码
    private String code;
}
