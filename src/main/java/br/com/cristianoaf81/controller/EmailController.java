package br.com.cristianoaf81.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import br.com.cristianoaf81.controller.docs.EmailControllerDocs;
import br.com.cristianoaf81.dto.request.EmailRequestDTO;
import br.com.cristianoaf81.services.EmailService;

@RestController
@RequestMapping("/api/email/v1")
public class EmailController implements EmailControllerDocs {

  @Autowired
  private EmailService emailService;

  @Override
  public ResponseEntity<String> sendEmailWithAttachment(String emailRequestJson, MultipartFile multipartFile) {
    // TODO Auto-generated method stub
    return null;
  }

  @PostMapping
  @Override
  public ResponseEntity<String> sendEmail(@RequestBody EmailRequestDTO emailRequestDTO) {
    emailService.sendSimpleEmail(emailRequestDTO);
    return new ResponseEntity<>("e-Mail sent with success", HttpStatus.OK);
  }
}
