package top.liubaiblog.masterstudio.service;

import top.liubaiblog.masterstudio.domain.message.EmailMessage;

/**
 * @author 留白
 * @description
 */
public interface EmailService {
    /**
     * 发送邮件
     */
    void send(EmailMessage emailMessage);
}
