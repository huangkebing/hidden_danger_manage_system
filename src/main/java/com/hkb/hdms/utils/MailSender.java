package com.hkb.hdms.utils;

/**
 * 邮件发送接口
 *
 * @author huangkebing
 * 2021/03/06
 */
public interface MailSender {
    /**
     * @param subject 邮件主题
     * @param to      目标邮箱
     * @param message 邮件信息
     */
    void sendMail(String subject, String[] message, String... to);
}
