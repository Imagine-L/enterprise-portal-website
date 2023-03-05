package top.liubaiblog.masterstudio.test;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import top.liubaiblog.masterstudio.util.jwt.JwtUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 留白
 * @description
 */
@Slf4j
@SpringBootTest
public class JwtTest {

    @Test
    public void createJwt() {
        HashMap<String, Object> claims = new HashMap<>();
        claims.put("username", "jack");
        String token = JwtUtils.create(claims);
        Map<String, Object> parse = JwtUtils.parse(token);
        Assertions.assertEquals("jack", parse.get("username"));
    }

}
