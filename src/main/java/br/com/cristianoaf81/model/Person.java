package br.com.cristianoaf81.model;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "person")
public class Person implements Serializable {
  
  private static final long serialVersionUID = 3250718952166047026L;
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "first_name", nullable = false, length = 80)
  private String firstName;

  @Column(name = "last_name", nullable = false, length = 80)
  private String lastName;

  @Column(nullable = false, length = 200)
  private String address;

  @Column(nullable = false, length = 6)
  private String gender;

  public Person() {}

  // id
  public void setId(Long id) { this.id = id ;}
  public Long getId() { return this.id; }
  
  // firstname
  public void setFirstName(String firstName) { this.firstName = firstName; }
  public String getFirstName() { return this.firstName; }

  // lastname
  public void setLastName(String lastName) { this.lastName = lastName; }
  public String getLastName() { return this.lastName; }

  // address
  public void setAddress(String address) { this.address = address; }
  public String getAddress() { return this.address; }

  // gender
  public void setGender(String gender) { this.gender = gender;  }
  public String getGender() { return this.gender; }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null) return false;
    if (getClass() != obj.getClass()) return false;
    Person p = (Person) obj;
    return id == p.getId() && 
      Objects.equals(firstName, p.getFirstName()) &&
      Objects.equals(lastName, p.getLastName()) &&
      Objects.equals(address, p.getAddress()) &&
      Objects.equals(gender, p.getGender());
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, firstName, lastName, address, gender);
  }

  @Override
  public String toString() {
    return "Person [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", address=" + address + ", gender="
        + gender + "]";
  }
}
