package br.com.cristianoaf81.dto.security;

import java.io.Serializable;
import java.util.Objects;

public class AccountCredentialsDTO implements Serializable {

  private static final long serialVersionUID = 8501218071697588261L;

  private String userName;

  private String password;

  private String fullName;

  public void setFullName(String fullName) {
    this.fullName = fullName;
  }

  public String getFullName() {
    return fullName;
  }

  public AccountCredentialsDTO() {
  }

  public AccountCredentialsDTO(String userName, String password, String fullName) {
    this.userName = userName;
    this.password = password;
    this.fullName = fullName;
  }

  public static long getSerialversionuid() {
    return serialVersionUID;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
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

    return Objects.equals(userName, dto.getUserName()) &&
        Objects.equals(password, dto.getPassword()) &&
        Objects.equals(fullName, dto.getFullName());
  }

  @Override
  public int hashCode() {
    return Objects.hash(userName, password, fullName);
  }

  @Override
  public String toString() {
    return "AccountCredentialsDTO ["
        + "userName=" + userName
        + ", password=" + password
        + ", fullName=" + fullName + "]";
  }

}
