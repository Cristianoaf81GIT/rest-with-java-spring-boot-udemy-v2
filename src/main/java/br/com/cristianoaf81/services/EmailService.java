package br.com.cristianoaf81.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.cristianoaf81.config.EmailConfig;
import br.com.cristianoaf81.dto.request.EmailRequestDTO;
import br.com.cristianoaf81.mail.EmailSender;

@Service
public class EmailService {

  @Autowired
  private EmailSender emailSender;

  @Autowired
  private EmailConfig emailConfig;

  public void sendSimpleEmail(EmailRequestDTO emailRequestDTO) {
    emailSender
        .to(emailRequestDTO.getTo())
        .withSubject(emailRequestDTO.getSubject())
        .withBodyMessage(emailRequestDTO.getBody())
        .send(emailConfig);
  }

}
