package top.liubaiblog.masterstudio.api.error.handler;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import top.liubaiblog.masterstudio.domain.enums.ResponseDataEnum;
import top.liubaiblog.masterstudio.util.http.HttpResponseUtils;
import top.liubaiblog.masterstudio.util.http.ResponseDataBuilder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author 留白
 * @description
 */
@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        HttpResponseUtils.write(response, ResponseDataBuilder.builder().build(ResponseDataEnum.CLIENT_LOGIN_EXCEPTION, authException.getMessage()));
    }
}
