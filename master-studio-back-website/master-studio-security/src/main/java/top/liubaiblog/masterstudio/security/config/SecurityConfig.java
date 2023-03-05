package top.liubaiblog.masterstudio.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import top.liubaiblog.masterstudio.common.exception.resource.StaticResourceException;
import top.liubaiblog.masterstudio.security.filter.TokenAuthenticationFilter;

/**
 * @author 留白
 * @description
 */
@Configuration
public class SecurityConfig {

    @Value("${swagger.enable:false}")
    private boolean swaggerEnable = false;

    @Autowired
    @SuppressWarnings("all")
    private AuthenticationConfiguration authenticationConfiguration;

    @Autowired
    private TokenAuthenticationFilter tokenAuthenticationFilter;

    @Autowired
    private AuthenticationEntryPoint authenticationEntryPoint;

    @Autowired
    @SuppressWarnings("all")
    private WebMvcProperties webMvcProperties;

    /**
     * 注入认证管理器
     */
    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    /**
     * 密码编码器
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 配置可以忽略的资源
     */
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> {
            String staticPath = webMvcProperties.getStaticPathPattern();
            if ("/**".equals(staticPath)) {
                throw new StaticResourceException("静态资源的路径不能为\"/**\"，建议修改为\"/static/**\"");
            }
            web.ignoring()
                    .antMatchers(staticPath);
            if (swaggerEnable) {
                web.ignoring().antMatchers("/swagger-ui.html", "/swagger-resources/**", "/webjars/**", "/v2/**", "/api/**");
            }
        };
    }

    /**
     * 配置http请求过滤策略
     */
    @Bean
    public SecurityFilterChain filterChain(@SuppressWarnings("all") HttpSecurity http) throws Exception {
        // 请求认证规则
        http.authorizeRequests()
                .antMatchers("/login", "/test", "/forget/**", "/file/download/**").permitAll()
                .anyRequest().authenticated();
        // 允许跨域
        http.cors();
        // 关闭csrf防护
        http.csrf().disable();
        // 关闭默认的session策略
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        // 关闭默认提供的登录和登出功能
        http.formLogin().disable();
        http.logout().disable();
        // 认证异常处理
        http.exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPoint);
        // 添加过滤器
        http.addFilterBefore(tokenAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

}
