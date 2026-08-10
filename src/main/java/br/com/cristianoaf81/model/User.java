package br.com.cristianoaf81.model;

import java.util.Collection;
import java.util.Objects;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "user")
public class User implements UserDetails {

  private static final long serialVersionUID = -2887023055008208350L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "user_name", unique = true)
  private String username;

  @Column(name = "full_name")
  private String fullName;

  @Column
  private String password;

  @Column(name = "account_non_expired")
  private Boolean accountNonExpired;

  @Column(name = "account_non_locked")
  private Boolean accountNonLocked;

  @Column(name = "credentials_non_expired")
  private Boolean credentialsNonExpired;

  @Column
  private Boolean enabled;

  public User() {
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public boolean isAccountNonExpired() {
    // TODO Auto-generated method stub
    return UserDetails.super.isAccountNonExpired();
  }

  @Override
  public boolean isAccountNonLocked() {
    // TODO Auto-generated method stub
    return UserDetails.super.isAccountNonLocked();
  }

  @Override
  public boolean isCredentialsNonExpired() {
    // TODO Auto-generated method stub
    return UserDetails.super.isCredentialsNonExpired();
  }

  @Override
  public boolean isEnabled() {
    // TODO Auto-generated method stub
    return UserDetails.super.isEnabled();
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getFullName() {
    return fullName;
  }

  public void setFullName(String fullName) {
    this.fullName = fullName;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public Boolean getAccountNonExpired() {
    return accountNonExpired;
  }

  public void setAccountNonExpired(Boolean accountNonExpired) {
    this.accountNonExpired = accountNonExpired;
  }

  public Boolean getAccountNonLocked() {
    return accountNonLocked;
  }

  public void setAccountNonLocked(Boolean accountNonLocked) {
    this.accountNonLocked = accountNonLocked;
  }

  public Boolean getCredentialsNonExpired() {
    return credentialsNonExpired;
  }

  public void setCredentialsNonExpired(Boolean credentialsNonExpired) {
    this.credentialsNonExpired = credentialsNonExpired;
  }

  public Boolean getEnabled() {
    return enabled;
  }

  public void setEnabled(Boolean enabled) {
    this.enabled = enabled;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null || getClass() != obj.getClass())
      return false;

    User u = (User) obj;
    return Objects.equals(getId(), u.getId()) &&
        Objects.equals(getUsername(), u.getUsername()) &&
        Objects.equals(getFullName(), u.getFullName()) &&
        Objects.equals(getAccountNonExpired(), u.getAccountNonExpired()) &&
        Objects.equals(getAccountNonLocked(), u.getAccountNonLocked()) &&
        Objects.equals(getCredentialsNonExpired(), u.getCredentialsNonExpired());
  }

  @Override
  public int hashCode() {
    return Objects
        .hash(
            getId(),
            getUsername(),
            getFullName(),
            getAccountNonExpired(),
            getAccountNonLocked(),
            getCredentialsNonExpired());
  }

  @Override
  public String toString() {
    return "User [id=" + id + ", username=" + username + ", fullName=" +
        fullName + ", accountNonExpired=" + accountNonExpired +
        ", accountNonLocked=" + accountNonLocked +
        ", credentialsNonExpired=" + credentialsNonExpired + ", enabled=" +
        enabled + "]";
  }
}
