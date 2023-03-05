package top.liubaiblog.masterstudio.security.filter;

import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import top.liubaiblog.masterstudio.common.constant.JwtConstants;
import top.liubaiblog.masterstudio.common.constant.RedisConstants;
import top.liubaiblog.masterstudio.common.exception.auth.TokenIllegalException;
import top.liubaiblog.masterstudio.domain.po.User;
import top.liubaiblog.masterstudio.util.http.HttpRequestUtils;
import top.liubaiblog.masterstudio.util.jwt.JwtUtils;
import top.liubaiblog.masterstudio.util.redis.RedisUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import static top.liubaiblog.masterstudio.common.constant.RedisConstants.AUTH_PREFIX;
import static top.liubaiblog.masterstudio.common.constant.RedisConstants.SEPARATOR;

/**
 * @author 留白
 * @description 请求认证过滤器，判断用户是否已经登录
 */
@Component
public class TokenAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private RedisUtils redisUtils;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 从请求头中获取token
        String token = request.getHeader(HttpRequestUtils.AUTHORIZATION_HEADER);
        if (!StringUtils.hasText(token)) {
            filterChain.doFilter(request, response);
            return;
        }
        // 解析token，并获取token在redis中对应的key
        String key = checkTokenAndGetRedisKey(token);
        // 根据token从redis中获取用户信息
        User currUser = redisUtils.valGetObject(key);
        if (Objects.isNull(currUser)) {
            throw new TokenIllegalException("token已过期");
        }
        // 将token信息注入到security上下文
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(currUser,
                null,
                Collections.emptyList());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        // 放行
        filterChain.doFilter(request, response);
    }

    /**
     * 检查token合法性并且获取该token在redis中的key值
     */
    private String checkTokenAndGetRedisKey(String token) {
        // 解析token
        Map<String, Object> parseMap = null;
        try {
            parseMap = JwtUtils.parse(token);
        } catch (ExpiredJwtException e) {
            return refreshToken(token);
        } catch (Exception e) {
            // 说明token非法
            throw new TokenIllegalException("非法的token");
        }
        Object currUid = parseMap.get(JwtConstants.UID_KEY);
        if (Objects.isNull(currUid)) {
            throw new TokenIllegalException("未获取到token信息");
        }
        return AUTH_PREFIX + SEPARATOR + currUid + SEPARATOR + token;
    }

    /**
     * 续签token，并返回新的redis key
     */
    private String refreshToken(String token) {
        String resultKey = null;
        // 判断redis中的token是否过期
        Set<String> keys = redisUtils.keys(AUTH_PREFIX + "*" + token);
        // 如果redis中的token也过期，需要重新登录
        if (keys.isEmpty()) {
            throw new TokenIllegalException("token已过期");
        }
        // redis中没有过期则续签
        for (String key : keys) {
            resultKey = key;
            redisUtils.expire(key, RedisConstants.AUTH_EXPIRE_SECONDS, TimeUnit.SECONDS);
            break;
        }
        return resultKey;
    }
}
