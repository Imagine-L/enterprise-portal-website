package top.liubaiblog.masterstudio.util.jwt;

import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.lang.Assert;
import top.liubaiblog.masterstudio.util.jwt.properties.JWTProperties;
import top.liubaiblog.masterstudio.util.spring.SpringAppUtils;

import java.util.Collections;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

/**
 * @author 留白
 * @description 生成token工具类
 */
public class JwtUtils {

    private final static JWTProperties jwtProperties;

    static {
        jwtProperties = (JWTProperties) SpringAppUtils.getBean(JWTProperties.class);
    }

    private JwtUtils() {
    }

    /**
     * 创建token
     */
    public static String create(Map<String, Object> claims) {
        return Jwts.builder()
                .signWith(SignatureAlgorithm.HS512, jwtProperties.getSecretKey())   // 签名算法和密钥
                .setId(UUID.randomUUID().toString())    // token的id
                .setClaims(claims)          // body参数
                .setIssuedAt(new Date())    // 签发时间
                .setExpiration(new Date(System.currentTimeMillis() + jwtProperties.getExpiration()))    // 过期时间
                .compact();
    }

    /**
     * 解析token
     */
    public static Map<String, Object> parse(String token) {
        Jwt parse = Jwts.parser()
                .setSigningKey(jwtProperties.getSecretKey())
                .parse(token);
        return (Map<String, Object>) parse.getBody();
    }

}
