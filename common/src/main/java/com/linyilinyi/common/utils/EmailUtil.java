package com.linyilinyi.common.utils;

import lombok.extern.slf4j.Slf4j;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Slf4j
public class EmailUtil {

    /**
     * 发送电子邮件。
     *
     * 通过SMTP协议使用QQ邮箱发送邮件。需要提供发送邮箱的用户名和密码。
     *
     * @param to 收件人的电子邮件地址。
     * @param subject 邮件的主题。
     * @param content 邮件的内容。
     * @throws MessagingException 如果邮件发送过程中出现错误。
     */
    public static void sendEmail(String to, String subject, String content) throws MessagingException {
        final String username = "1830280916@qq.com"; // 替换为你的邮箱用户名
        final String password = "fvxemtlymkthechc"; // 替换为你的邮箱密码
        // 配置邮件服务器的属性
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.host", "smtp.qq.com");
        props.put("mail.smtp.port", "465");
        props.put("mail.smtp.ssl.enable", "true");
        // 创建会话对象
        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            // 创建邮件消息对象
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject(subject);
            message.setText(content);

            // 发送邮件
            Transport.send(message);
            log.info("验证码发送成功");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}