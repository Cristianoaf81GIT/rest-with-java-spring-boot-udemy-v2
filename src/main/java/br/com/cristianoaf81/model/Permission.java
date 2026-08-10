package br.com.cristianoaf81.model;

import java.util.Objects;

import org.springframework.security.core.GrantedAuthority;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "permission")
public class Permission implements GrantedAuthority {

  private static final long serialVersionUID = 5435779964688840080L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column
  private String description;

  public Permission() {
  }

  @Override
  public String getAuthority() {
    return this.description;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null || getClass() != obj.getClass())
      return false;
    Permission p = (Permission) obj;
    return Objects.equals(id, p.getId()) &&
        Objects.equals(description, p.getDescription());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getId(), getDescription());
  }

  @Override
  public String toString() {
    return "Permission [id=" + id + ", description=" + description + "]";
  }
}
