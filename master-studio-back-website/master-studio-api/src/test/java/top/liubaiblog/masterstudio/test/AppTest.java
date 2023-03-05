package top.liubaiblog.masterstudio.test;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import top.liubaiblog.masterstudio.util.file.StorageUnitUtil;

/**
 * @author 留白
 * @description
 */
@SpringBootTest
public class AppTest {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void passTest() {
        String md5Pwd = "e10adc3949ba59abbe56e057f20f883e";
//        String encode = passwordEncoder.encode(md5Pwd);
//        String a = null;
//        System.out.println(a + "");
        String s = StorageUnitUtil.autoConvert(2073741824, StorageUnitUtil.Unit.B, " ", "");
        System.out.println("单位：" + s);
    }

}
