package br.com.cristianoaf81.dto.security;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class TokenDTO implements Serializable {

  private static final long serialVersionUID = 8146823603609760873L;

  private String username;

  private String password;

  private Boolean authenticated;

  private Date created;

  private Date expiration;

  private String accessToken;

  private String refreshToken;

  public TokenDTO() {
  }

  public TokenDTO(
      String username,
      Boolean authenticated,
      Date created,
      Date expiration,
      String accessToken,
      String refreshToken) {
    this.username = username;
    this.authenticated = authenticated;
    this.created = created;
    this.expiration = expiration;
    this.accessToken = accessToken;
    this.refreshToken = refreshToken;
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

  public Boolean getAuthenticated() {
    return authenticated;
  }

  public void setAuthenticated(Boolean authenticated) {
    this.authenticated = authenticated;
  }

  public Date getCreated() {
    return created;
  }

  public void setCreated(Date created) {
    this.created = created;
  }

  public Date getExpiration() {
    return expiration;
  }

  public void setExpiration(Date expiration) {
    this.expiration = expiration;
  }

  public String getAccessToken() {
    return accessToken;
  }

  public void setAccessToken(String accessToken) {
    this.accessToken = accessToken;
  }

  public String getRefreshToken() {
    return refreshToken;
  }

  public void setRefreshToken(String refreshToken) {
    this.refreshToken = refreshToken;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }

    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }

    TokenDTO dto = (TokenDTO) obj;

    return Objects.equals(getUsername(), dto.getUsername()) &&
        Objects.equals(getPassword(), dto.getPassword()) &&
        Objects.equals(getAuthenticated(), dto.getAuthenticated()) &&
        Objects.equals(getAccessToken(), dto.getAccessToken()) &&
        Objects.equals(getCreated(), dto.getCreated()) &&
        Objects.equals(getExpiration(), dto.getExpiration()) &&
        Objects.equals(getRefreshToken(), dto.getRefreshToken());
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        getUsername(),
        getAccessToken(),
        getAuthenticated(),
        getCreated(),
        getExpiration(),
        getRefreshToken(),
        getPassword());
  }

  @Override
  public String toString() {
    return "TokenDTO[" +
        "username='" + username + '\'' +
        ", authenticated=" + authenticated +
        ", created=" + created +
        ", expiration=" + expiration +
        ", accessToken='" + accessToken + '\'' +
        ", refreshToken='" + refreshToken + '\'' +
        ']';
  }
}
