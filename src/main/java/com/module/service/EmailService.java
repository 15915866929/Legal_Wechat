package com.module.service;


import com.module.entity.EmailRecipient;
import com.module.entity.EmailSender;
import com.module.vo.EmailFileVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.ListUtils;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import java.util.Date;
import java.util.List;
import java.util.Properties;

/**
 * @author chc
 * @create 2017-11-21 20:10
 **/
@Service("emailService")
public class EmailService {

    public void sendEmailToOperator(String sendEmail, List<String> fileIds) throws Exception {
//        EmailSender sender = daoFactory.getEmailSenderDao().findFristToDocument(new Document(),null,EmailSender.class);
//        List<EmailRecipient> recipients = new ArrayList<>();
//        EmailRecipient er = new EmailRecipient();
//        er.setEmail(sendEmail);
//        er.setType(1);
//        recipients.add(er);
//        List<EmailFileVo> filePaths =null;
//        if(!ListUtils.isEmpty(fileIds)){
//            filePaths = daoFactory.getFileDao().findFilePaths(fileIds);
//        }
//
//        if(sender==null || StringUtils.isEmpty(sender.getSmtpEmail())){
//            throw new ResultException("未配置邮箱发送者");
////            throwBaseException(ErrorCode.Email_701,"未配置邮箱发送者");
//        }
//        prepareEmail(sender, recipients, "表格下载", "表格下载", filePaths);
    }

    public void prepareEmail(EmailSender sender, List<EmailRecipient> recipients, String title, String content, List<EmailFileVo> filePaths) throws Exception {
        // 1. 创建参数配置, 用于连接邮件服务器的参数配置
        // 参数配置
        Properties props = new Properties();
        // 使用的协议（JavaMail规范要求）
        props.setProperty("mail.transport.protocol", "smtp");
        // 发件人的邮箱的 SMTP 服务器地址
        props.setProperty("mail.smtp.host", sender.getSmtpEmail());
        // 需要请求认证
        props.setProperty("mail.smtp.auth", "true");

        // PS: 某些邮箱服务器要求 SMTP 连接需要使用 SSL 安全认证 (为了提高安全性, 邮箱支持SSL连接, 也可以自己开启),
        //     如果无法连接邮件服务器, 仔细查看控制台打印的 log, 如果有有类似 “连接失败, 要求 SSL 安全连接” 等错误,
        //     打开下面 /* ... */ 之间的注释代码, 开启 SSL 安全连接。
        // SMTP 服务器的端口 (非 SSL 连接的端口一般默认为 25, 可以不添加, 如果开启了 SSL 连接,
        //                  需要改为对应邮箱的 SMTP 服务器的端口, 具体可查看对应邮箱服务的帮助,
        //                  QQ邮箱的SMTP(SLL)端口为465或587, 其他邮箱自行去查看)
        final String smtpPort = "465";
        props.setProperty("mail.smtp.port", smtpPort);
        props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.setProperty("mail.smtp.socketFactory.fallback", "false");
        props.setProperty("mail.smtp.socketFactory.port", smtpPort);

        //解决附件名过长问题
        System.setProperty("mail.mime.splitlongparameters","false");


        // 2. 根据配置创建会话对象, 用于和邮件服务器交互
        Session session = Session.getInstance(props);
        // 设置为debug模式, 可以查看详细的发送 log
        session.setDebug(false);

        // 3. 创建一封邮件(编辑内容、附近等)
        MimeMessage message = createMimeMessage(session, sender, recipients, title, content, filePaths);

        // 4. 根据 Session 获取邮件传输对象
        Transport transport = session.getTransport();

        // 5. 使用 邮箱账号 和 密码 连接邮件服务器, 这里认证的邮箱必须与 message 中的发件人邮箱一致, 否则报错
        //
        //    PS_01: 成败的判断关键在此一句, 如果连接服务器失败, 都会在控制台输出相应失败原因的 log,
        //           仔细查看失败原因, 有些邮箱服务器会返回错误码或查看错误类型的链接, 根据给出的错误
        //           类型到对应邮件服务器的帮助网站上查看具体失败原因。
        //
        //    PS_02: 连接失败的原因通常为以下几点, 仔细检查代码:
        //           (1) 邮箱没有开启 SMTP 服务;
        //           (2) 邮箱密码错误, 例如某些邮箱开启了独立密码;
        //           (3) 邮箱服务器要求必须要使用 SSL 安全连接;
        //           (4) 请求过于频繁或其他原因, 被邮件服务器拒绝服务;
        //           (5) 如果以上几点都确定无误, 到邮件服务器网站查找帮助。
        //
        //    PS_03: 仔细看log, 认真看log, 看懂log, 错误原因都在log已说明。
        transport.connect(sender.getEmail(), sender.getPwd());

        // 6. 发送邮件, 发到所有的收件地址, message.getAllRecipients() 获取到的是在创建邮件对象时添加的所有收件人, 抄送人, 密送人
        transport.sendMessage(message, message.getAllRecipients());

        // 7. 关闭连接
        transport.close();
    }


    /**
     * 创建一封只包含文本的简单邮件
     * @param session 和服务器交互的会话
     * @param sender 发件人邮箱信息
     * @param recipients 收件人邮箱(数组)
     * @param title 邮件标题
     * @param content 邮件文本内容
     * @param filePaths 附件路径(数组)
     * @return
     * @throws Exception
     */
    public static MimeMessage createMimeMessage(Session session, EmailSender sender, List<EmailRecipient> recipients, String title, String content, List<EmailFileVo> filePaths) throws Exception {
        // 1. 创建一封邮件
        MimeMessage message = new MimeMessage(session);
        Multipart mp = new MimeMultipart();
        // 2. From: 发件人（昵称有广告嫌疑，避免被邮件服务器误认为是滥发广告以至返回失败，请修改昵称）
        if (!StringUtils.isEmpty(sender.getNickName())) {
            InternetAddress internetAddress = new InternetAddress(sender.getEmail(), sender.getNickName(), "UTF-8");
            message.setFrom(internetAddress);
        }else {
            InternetAddress internetAddress = new InternetAddress(sender.getEmail());
            message.setFrom(internetAddress);
        }

        // 3. To: 收件人（可以增加多个收件人、抄送、密送）
        for (EmailRecipient er : recipients) {
            Address address;
            if (!StringUtils.isEmpty(er.getNickName())) {
                address = new InternetAddress(er.getEmail(), er.getNickName(), "UTF-8");
            }else {
                address = new InternetAddress(er.getEmail());
            }
            switch (er.getType()){
                case 1:
                    message.setRecipient(MimeMessage.RecipientType.TO, address);
                    break;
                case 2:
                    message.setRecipient(MimeMessage.RecipientType.CC, address);
                    break;
                case 3:
                    message.setRecipient(MimeMessage.RecipientType.BCC, address);
                    break;
                default:
                    message.setRecipient(MimeMessage.RecipientType.TO, address);
                    break;
            }
        }
        // message.setRecipient(MimeMessage.RecipientType.BCC, new InternetAddress("1021488816@qq.com", "谁啊", "UTF-8"));

        // 4. Subject: 邮件主题（标题有广告嫌疑，避免被邮件服务器误认为是滥发广告以至返回失败，请修改标题）
        message.setSubject(title, "UTF-8");

        // 5. Content: 邮件正文（可以使用html标签）（内容有广告嫌疑，避免被邮件服务器误认为是滥发广告以至返回失败，请修改发送内容）
        BodyPart bp = new MimeBodyPart();
        bp.setContent(content, "text/html;charset=utf-8");
        mp.addBodyPart(bp);
        if (!ListUtils.isEmpty(filePaths)) {
            for (EmailFileVo path : filePaths) {
                bp = new MimeBodyPart();
                FileDataSource fds = new FileDataSource(path.getFilePath());
                bp.setDataHandler(new DataHandler(fds));

                String fileName = MimeUtility.encodeText(path.getFileName());
                bp.setFileName(fileName);
                mp.addBodyPart(bp);
            }
        }
        message.setContent(mp);

        // 6. 设置发件时间
        message.setSentDate(new Date());

        // 7. 保存设置
        message.saveChanges();

        return message;
    }
}
