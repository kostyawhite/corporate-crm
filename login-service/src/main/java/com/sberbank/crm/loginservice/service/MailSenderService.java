package com.sberbank.crm.loginservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailSenderService {
    @Value("${spring.mail.username}")
    private String username;

    @Value("${spring.app.url}")
    private String url;

    @Autowired
    private JavaMailSender mailSender;

    public void sendConfirmMsg(String emailTo, String name, String code) {
        SimpleMailMessage mailMsg = new SimpleMailMessage();

        String msg = String.format(
                "Добрый день, %s!\n" +
                "Ваша ссылка для подтверждения регистрации:\n" +
                "<a href=\"%s/login?confirmKey=%s\">Подтвердить</a>",
                name, url, code);

        mailMsg.setFrom(username);
        mailMsg.setTo(emailTo);
        mailMsg.setSubject("Код активации");
        mailMsg.setText(msg);

        mailSender.send(mailMsg);
    }
}
