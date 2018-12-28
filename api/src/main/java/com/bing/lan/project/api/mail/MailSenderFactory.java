package com.bing.lan.project.api.mail;

/**
 * 发件箱工厂
 *
 * @author MZULE
 */
public class MailSenderFactory {

    /**
     * 服务邮箱
     */
    private static SimpleMailSender serviceSms = null;

    /**
     * 获取邮箱
     *
     * @param type 邮箱类型
     * @return 符合类型的邮箱
     */
    public static SimpleMailSender getSender(MailSenderType type) {
        if (type == MailSenderType.SERVICE) {
            //if (serviceSms == null) {
                serviceSms = new SimpleMailSender("111112013@163.com",
                        "11111111111111");
            //}
            return serviceSms;
        }
        return null;
    }
}