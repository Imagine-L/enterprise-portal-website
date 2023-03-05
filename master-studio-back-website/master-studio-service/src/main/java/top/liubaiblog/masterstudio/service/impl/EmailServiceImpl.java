package top.liubaiblog.masterstudio.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import top.liubaiblog.masterstudio.domain.message.EmailMessage;
import top.liubaiblog.masterstudio.domain.enums.EmailTypeEnums;
import top.liubaiblog.masterstudio.service.EmailService;

import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.Date;
import java.util.Objects;

/**
 * @author 留白
 * @description
 */
@Slf4j
@Service
public class EmailServiceImpl implements EmailService {

    //注入邮件工具类
    @Autowired
    private JavaMailSender javaMailSender;

    // 获取用户名
    @Value("${spring.mail.username}")
    private String sendMailer;

    @Override
    public void send(EmailMessage emailMessage) {
        if (Objects.equals(EmailTypeEnums.HTMl.getName(), emailMessage.getType())) {
            sendHtmlMail(emailMessage);
        } else {
            sendSimpleMail(emailMessage);
        }
    }

    public void sendSimpleMail(EmailMessage emailMessage) {
        SimpleMailMessage message = new SimpleMailMessage();
        //邮件发件人
        message.setFrom(sendMailer);
        //邮件收件人 1或多个
        message.setTo(emailMessage.getSendTo().split(","));
        //邮件主题
        message.setSubject(emailMessage.getSubject());
        //邮件内容
        message.setText(emailMessage.getText());
        //邮件发送时间
        message.setSentDate(new Date());
        try {
            javaMailSender.send(message);
            log.info("发送txt邮件成功: {} -> {} ", sendMailer, emailMessage.getSendTo());
        } catch (Exception e) {
            log.error("发送邮件时发生异常！", e);
        }
    }

    public void sendHtmlMail(EmailMessage emailMessage) {
        MimeMessage message = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            //邮件发件人
            helper.setFrom(sendMailer);
            //邮件收件人 1或多个
            helper.setTo(emailMessage.getSendTo().split(","));
            //邮件主题
            helper.setSubject(emailMessage.getSubject());
            //邮件内容
            helper.setText(emailMessage.getText(), true);
            //邮件发送时间
            helper.setSentDate(new Date());
            // 发送附件
            String filePath = emailMessage.getFilePath();
            if (StringUtils.hasText(filePath)) {
                FileSystemResource file = new FileSystemResource(new File(filePath));
                String fileName = filePath.substring(filePath.lastIndexOf(File.separator));
                helper.addAttachment(fileName, file);
            }
            javaMailSender.send(message);
            log.info("发送html邮件成功:{} -> {}", sendMailer, emailMessage.getSendTo());
        } catch (Exception e) {
            log.error("发送邮件时发生异常！", e);
        }
    }


}
