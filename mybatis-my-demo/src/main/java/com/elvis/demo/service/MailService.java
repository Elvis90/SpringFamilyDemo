package com.elvis.demo.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.internet.MimeMessage;
import java.text.SimpleDateFormat;

/**
 * @author Elvis
 * @create 2019-07-15 16:40
 * 邮件发送测试
 */
@Service
@Slf4j
public class MailService {
    private final String MAIL_SENDER="453122235@qq.com";

    @Autowired
    JavaMailSender javaMailSender;
    @Autowired
    TemplateEngine templateEngine;
    //简单文件邮件
    public void sendSimpleMail(){
        try {
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            //邮件发送人
            simpleMailMessage.setFrom(MAIL_SENDER);
            //邮件接收人
            simpleMailMessage.setTo("15123837030@139.com");
            //邮件主题
            simpleMailMessage.setSubject("测试主题");
            //邮件内容
            simpleMailMessage.setText("测试内容");
            javaMailSender.send(simpleMailMessage);
        } catch (Exception e) {
            log.error("邮件发送失败:", e.getMessage());
        }
    }

    //使用thymeleaf模版发送邮件

    public void sendTemplateMail() {
        MimeMessage mimeMailMessage = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            mimeMailMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMailMessage, true);
            mimeMessageHelper.setFrom(MAIL_SENDER);
            mimeMessageHelper.setTo("15123837030@139.com");
            mimeMessageHelper.setSubject("模版邮件测试");

            Context ctx = new Context();
            ctx.setVariable("name", "周杰伦");
            ctx.setVariable("workID", "000002");
            ctx.setVariable("contractTerm", "100");
            ctx.setVariable("beginContract", sdf.parse("2000-01-01"));
            ctx.setVariable("endContract", sdf.parse("2100-01-01"));
            ctx.setVariable("departmentName", "杰威尔音乐");
            ctx.setVariable("posName", "音乐总监");
            String mail = templateEngine.process("email.html", ctx);
            mimeMessageHelper.setText(mail, true);
            javaMailSender.send(mimeMailMessage);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("邮件发送失败", e.getMessage());
        }

    }
}
