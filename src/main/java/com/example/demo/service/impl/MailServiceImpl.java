package com.example.demo.service.impl;

import com.example.demo.model.data.DataMail;
import com.example.demo.service.MailService;
import com.example.demo.service.VoucherSevice;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

@Service
public class MailServiceImpl implements MailService {
    private final JavaMailSender mailSender;
    private final SpringTemplateEngine templateEngine;
    private final VoucherSevice voucherSevice;

    @Autowired
    public MailServiceImpl(JavaMailSender mailSender,SpringTemplateEngine templateEngine,VoucherSevice voucherSevice){
        this.mailSender=mailSender;
        this.templateEngine=templateEngine;
        this.voucherSevice=voucherSevice;
    }
    @Override
    public void SendHtmlMailOrder(DataMail dataMail, String templateName) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message,true,"utf-8");

        Context context = new Context();
        context.setVariables(dataMail.getProps());

        String html = templateEngine.process(templateName,context);

        helper.setTo(dataMail.getTo());
        helper.setSubject("Thông báo");
        helper.setText(html,true);
        mailSender.send(message);
    }

    @Override
    public void SendHtmlMailSubscribe(DataMail dataMail, String templateName) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message,true,"utf-8");

        Context context = new Context();
        context.setVariables(dataMail
                .getProps());
        String html = templateEngine.process(templateName,context);

        helper.setTo(dataMail.getTo());
        helper.setSubject("Cảm ơn bạn đã đăng ký nhận thông báo từ nhà hàng Wowwraps của chúng tôi!");
        helper.setText(html,true);
        mailSender.send(message);
    }

    @Override
    public void SendHtmlMailBook(DataMail dataMail, String templateName) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message,true,"utf-8");

        Context context = new Context();
        context.setVariables(dataMail
                .getProps());
        String html = templateEngine.process(templateName,context);

        helper.setTo(dataMail.getTo());
        helper.setSubject("Xác nhận Đặt Bàn tại Wowwraps");
        helper.setText(html,true);
        mailSender.send(message);
    }

    @Override
    public void SendHtmlMailBookCancel(DataMail dataMail, String templateName) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message,true,"utf-8");

        Context context = new Context();
        context.setVariables(dataMail
                .getProps());
        String html = templateEngine.process(templateName,context);

        helper.setTo(dataMail.getTo());
        helper.setSubject("Thông báo từ Wowwraps - Từ chối yêu cầu đặt bàn");
        helper.setText(html,true);
        mailSender.send(message);
    }
}
