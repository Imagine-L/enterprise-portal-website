package top.liubaiblog.masterstudio.message.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import top.liubaiblog.masterstudio.common.constant.RabbitConstants;

/**
 * @author 留白
 * @description
 */
@Configuration
@EnableRabbit
public class RabbitConfig {

    /**
     * 邮件交换机
     */
    @Bean
    public DirectExchange emailExchange() {
        return new DirectExchange(RabbitConstants.EMAIL_EXCHANGE, true, false);
    }

    /**
     * 邮件队列
     */
    @Bean
    public Queue emailQueue() {
        return QueueBuilder
                .durable(RabbitConstants.EMAIL_QUEUE)
                .build();
    }

    /**
     * 邮件路由键
     */
    @Bean
    public Binding emailBinding() {
        return BindingBuilder
                .bind(emailQueue())
                .to(emailExchange())
                .with(RabbitConstants.EMAIL_EXCHANGE_BINDING_QUEUE);
    }

    /**
     * 上传交换机
     */
    @Bean
    public DirectExchange fileExchange() {
        return new DirectExchange(RabbitConstants.FILE_EXCHANGE, true, false);
    }

    /**
     * 上传队列
     */
    @Bean
    public Queue fileUploadQueue() {
        return QueueBuilder
                .durable(RabbitConstants.FILE_UPLOAD_QUEUE)
                .build();
    }

    /**
     * 上传路由键
     */
    @Bean
    public Binding fileUploadExchangeBindingQueue() {
        return BindingBuilder
                .bind(fileUploadQueue())
                .to(fileExchange())
                .with(RabbitConstants.FILE_UPLOAD_EXCHANGE_BINDING_QUEUE);
    }

    /**
     * 文件删除队列
     */
    @Bean
    public Queue fileRemoveQueue() {
        return QueueBuilder
                .durable(RabbitConstants.FILE_REMOVE_QUEUE)
                .build();
    }

    /**
     * 文件删除路由键
     */
    @Bean
    public Binding fileRemoveExchangeBindingQueue() {
        return BindingBuilder
                .bind(fileRemoveQueue())
                .to(fileExchange())
                .with(RabbitConstants.FILE_REMOVE_EXCHANGE_BINDING_QUEUE);
    }

    /**
     * 日志交换机
     */
    @Bean
    public DirectExchange logExchange() {
        return new DirectExchange(RabbitConstants.LOG_EXCHANGE, true, false);
    }

    /**
     * 日志保存队列
     */
    @Bean
    public Queue logSaveQueue() {
        return QueueBuilder
                .durable(RabbitConstants.LOG_SAVE_QUEUE)
                .build();
    }

    /**
     * 日志保存队列路由键
     */
    @Bean
    public Binding logSaveExchangeBindingQueue() {
        return BindingBuilder
                .bind(logSaveQueue())
                .to(logExchange())
                .with(RabbitConstants.LOG_SAVE_EXCHANGE_BINDING_QUEUE);
    }

    /**
     * rabbit消息转换器
     */
    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

}
