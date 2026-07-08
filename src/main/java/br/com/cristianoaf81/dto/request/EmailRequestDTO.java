package br.com.cristianoaf81.dto.request;

import java.util.Objects;

public class EmailRequestDTO {

  private String to;
  private String subject;
  private String body;

  public EmailRequestDTO() {
  }

  public String getTo() {
    return to;
  }

  public void setTo(String to) {
    this.to = to;
  }

  public String getSubject() {
    return subject;
  }

  public void setSubject(String subject) {
    this.subject = subject;
  }

  public String getBody() {
    return body;
  }

  public void setBody(String body) {
    this.body = body;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;

    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }

    EmailRequestDTO dto = (EmailRequestDTO) obj;

    return to.equals(dto.getTo())
        && Objects.equals(subject, dto.getSubject())
        && Objects.equals(body, dto.getBody());
  }

  @Override
  public int hashCode() {
    return Objects.hash(to, subject, body);
  }
}
