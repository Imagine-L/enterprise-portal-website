package top.liubaiblog.masterstudio.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import top.liubaiblog.masterstudio.util.redis.RedisUtils;

/**
 * @author 留白
 * @description
 */
@SpringBootTest
public class RedisTest {

    @Autowired
    private RedisUtils redisUtils;

    @Test
    public void testRedisConnection() {
        redisUtils.valSet("connectionTag", "测试连通性");
        String connectionTag = redisUtils.valGet("connectionTag");
        Assertions.assertEquals(connectionTag, "测试连通性");
        redisUtils.delete("connectionTag");
    }

}
