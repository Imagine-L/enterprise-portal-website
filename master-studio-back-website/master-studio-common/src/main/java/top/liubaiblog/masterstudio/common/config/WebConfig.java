package top.liubaiblog.masterstudio.common.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import top.liubaiblog.masterstudio.common.interceptor.HttpInterceptor;
import top.liubaiblog.masterstudio.domain.prop.ForgetPasswordMessageProperties;

import javax.annotation.PostConstruct;

/**
 * @author 留白
 * @description
 */
@Configuration
@EnableConfigurationProperties(ForgetPasswordMessageProperties.class)
@MapperScan("top.liubaiblog.masterstudio.dao.mapper")
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private HttpInterceptor httpInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(httpInterceptor)
                .addPathPatterns("/**");
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")  // 服务端允许跨域的路径
                .allowedOriginPatterns("*")     // 设置允许跨域请求的域名
                .allowCredentials(true)         // 是否允许Cookies
                .allowedMethods("GET", "POST", "DELETE", "PUT") // 允许请求的方式
                .allowedHeaders("*")    // 允许的请求头属性
                .maxAge(3600);          // 跨域允许的时间
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }
}
