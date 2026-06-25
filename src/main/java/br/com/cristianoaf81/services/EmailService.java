package br.com.cristianoaf81.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.cristianoaf81.config.EmailConfig;
import br.com.cristianoaf81.mail.EmailSender;

@Service
public class EmailService {

  @Autowired
  private EmailSender emailSender;

  @Autowired
  private EmailConfig emailConfig;

  public void sendSimpleEmail(String to, String subject, String body) {
    emailSender
        .to(to)
        .withSubject(subject)
        .withBodyMessage(body)
        .send(emailConfig);
  }

}
