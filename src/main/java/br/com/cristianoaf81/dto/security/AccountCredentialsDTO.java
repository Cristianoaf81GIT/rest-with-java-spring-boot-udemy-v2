package br.com.cristianoaf81.dto.security;

import java.io.Serializable;
import java.util.Objects;

public class AccountCredentialsDTO implements Serializable {

  private static final long serialVersionUID = 8501218071697588261L;

  private String username;

  private String password;

  public AccountCredentialsDTO() {
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }

    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }

    AccountCredentialsDTO dto = (AccountCredentialsDTO) obj;

    return Objects.equals(username, dto.getUsername()) &&
        Objects.equals(password, dto.getPassword());
  }

  @Override
  public int hashCode() {
    return Objects.hash(username, password);
  }

  @Override
  public String toString() {
    return "AccountCredentialsDTO ["
        + "username=" + username
        + ", password=" + password + "]";
  }
}
