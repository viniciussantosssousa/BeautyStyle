package com.salao.agendamentos.service;

import lombok.AllArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
@AllArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    @Async // Executa o envio de e-mail em uma thread separada
    public void send(String to, String subject, String emailBody) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
            helper.setText(emailBody, true); // true para indicar que é HTML
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setFrom("seu-email@gmail.com"); // Opcional: mesmo e-mail do properties
            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            // Em um projeto real, logar este erro é crucial
            throw new IllegalStateException("Falha ao enviar e-mail", e);
        }
    }
}