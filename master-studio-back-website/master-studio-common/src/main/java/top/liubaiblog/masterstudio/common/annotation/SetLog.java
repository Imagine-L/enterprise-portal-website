package top.liubaiblog.masterstudio.common.annotation;

import org.springframework.core.annotation.AliasFor;
import top.liubaiblog.masterstudio.domain.enums.LogModuleEnums;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author 留白
 * @description
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface SetLog {

    /**
     * 操作模块
     */
    LogModuleEnums module();
    /**
     * 操作类型
     */
    String operType() default "";
}
