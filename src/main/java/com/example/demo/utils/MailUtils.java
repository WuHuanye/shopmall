package com.example.demo.utils;

import org.springframework.mail.SimpleMailMessage;

/**
 * 发送邮件
 *
 * @author Intel-Meeting
 * @create 2019-09-06 14:53
 */
public class MailUtils {
    private static final String USER = ""; // 发件人称号，同邮箱地址
    private static final String PASSWORD = ""; // 如果是qq邮箱可以使户端授权码，或者登录密码

    /**
     * 发送邮件
     *
     * @param fromEmail 发送者
     * @param email     接收者
     * @param subject   主题
     * @param text      文本
     * @return 返回SimpleMailMessage对象
     */
    public static SimpleMailMessage getMailMessage(String fromEmail, String email, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom(fromEmail);
        message.setTo(email);

        message.setSubject(subject);
        message.setText(text);

        return message;
    }

}
