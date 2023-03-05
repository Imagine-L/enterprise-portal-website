package top.liubaiblog.masterstudio.message.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import top.liubaiblog.masterstudio.common.constant.RabbitConstants;
import top.liubaiblog.masterstudio.domain.message.EmailMessage;
import top.liubaiblog.masterstudio.service.EmailService;

import java.io.IOException;

/**
 * @author 留白
 * @description
 */
@Slf4j
@Component
public class EmailListener {

    @Autowired
    private EmailService emailService;

    /**
     * 监听队列
     */
    @RabbitListener(queues = RabbitConstants.EMAIL_QUEUE)
    public void receive(EmailMessage message) throws IOException {
        Assert.notNull(message, "收到空消息");
        emailService.send(message);
        log.info("发送邮件成功 -> {}", message);
    }
}
