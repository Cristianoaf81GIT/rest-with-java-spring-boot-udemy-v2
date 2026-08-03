package br.com.cristianoaf81.services;

import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

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

  public void sendEmailWithAttachment(String emailRequestJson, MultipartFile attachment) {
    File tempFile = null;
    try {
      EmailRequestDTO emailRequestDTO = new ObjectMapper().readValue(emailRequestJson, EmailRequestDTO.class);
      tempFile = File.createTempFile("attachment", attachment.getOriginalFilename());
      attachment.transferTo(tempFile);
      emailSender
          .to(emailRequestDTO.getTo())
          .withSubject(emailRequestDTO.getSubject())
          .withBodyMessage(emailRequestDTO.getSubject())
          .attach(tempFile.getAbsolutePath())
          .send(emailConfig);
    } catch (JsonProcessingException e) {
      throw new RuntimeException("Error parsing email request JSON", e);
    } catch (IOException e) {
      throw new RuntimeException("Error creating temporary file for attachment", e);
    } finally {
      if (tempFile != null && tempFile.exists()) {
        tempFile.delete();
      }
    }
  }

}
