package top.liubaiblog.masterstudio.api.config;

import com.google.common.base.Predicates;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author 留白
 * @description
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Value("${swagger.enable:false}")
    private boolean enable = false;

    //构建 api文档的详细信息函数,注意这里的注解引用的是哪个
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                //页面标题
                .title("大师工作室官网后台接口文档")
                //创建人
                .contact(new Contact("刘佳俊", "htliubaiblogtp://www..top/", null))
                //版本号
                .version("1.0")
                .build();
    }

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .pathMapping("/")
                .enable(enable)
                .select() // 选择那些路径和api会生成document
                .apis(RequestHandlerSelectors.basePackage("top.liubaiblog.masterstudio.api.controller"))
                .paths(PathSelectors.any())
                .build();
    }
}
