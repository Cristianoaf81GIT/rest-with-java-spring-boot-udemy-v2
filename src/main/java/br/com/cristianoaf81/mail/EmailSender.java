package br.com.cristianoaf81.mail;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.StringTokenizer;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.cristianoaf81.config.EmailConfig;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.AddressException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

@Component
public class EmailSender implements Serializable {

  private static final long serialVersionUID = -1207893751838651292L;

  Logger logger = LoggerFactory.getLogger(EmailSender.class);

  private final JavaMailSender mailSender;
  private String to;
  private String subject;
  private String body;
  private ArrayList<InternetAddress> recipients = new ArrayList<>();
  private File attachment;

  public EmailSender(JavaMailSender mailSender) {
    this.mailSender = mailSender;
  }

  public EmailSender to(String to) {
    this.to = to;
    this.recipients = getRecipients(to);
    return this;
  }

  public EmailSender setSubject(String subject) {
    this.subject = subject;
    return this;
  }

  public EmailSender withMessage(String body) {
    this.body = body;
    return this;
  }

  public EmailSender attach(String fileDir) {
    this.attachment = new File(fileDir);
    return this;
  }

  public void send(EmailConfig config) {
    MimeMessage message = mailSender.createMimeMessage();
    try {
      MimeMessageHelper helper = new MimeMessageHelper(message, true);
      helper.setFrom(config.getUsername());
      helper.setTo(recipients.toArray(new InternetAddress[0]));
      helper.setSubject(subject);
      helper.setText(body, true);
      if (attachment != null) {
        helper.addAttachment(attachment.getName(), attachment);
        mailSender.send(message);
      }
      logger.info("Email sent to %s with the subject '%s'%n", to, subject);
      reset();
    } catch (MessagingException me) {
      throw new RuntimeException("Error sending e-mail", me);
    }

  }

  private void reset() {
    this.to = null;
    this.subject = null;
    this.body = null;
    this.recipients = null;
    this.attachment = null;
  }

  private ArrayList<InternetAddress> getRecipients(String to2) {
    String toWithoutSpaces = to.replaceAll("\\s", "");
    StringTokenizer tok = new StringTokenizer(toWithoutSpaces, ";");
    ArrayList<InternetAddress> recipientsList = new ArrayList<>();
    while (tok.hasMoreElements()) {
      try {
        recipientsList.add(new InternetAddress(tok.nextElement().toString()));
      } catch (AddressException e) {
        throw new RuntimeException(e);
      }
    }
    return recipientsList;
  }
}
