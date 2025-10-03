package br.com.cristianoaf81.dto.v2;

import java.io.Serializable;
import java.util.Objects;
import java.util.Date;

public class PersonDTOV2 implements Serializable {
  
  private static final long serialVersionUID = 7008765778800736478L;

  private Long id;

  private String firstName;

  private String lastName;

  private String address;

  private String gender;

  public PersonDTOV2() {}

  private Date birthDate;

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

  // birthDate
  public void setBirthDate(Date birthDate) { this.birthDate = birthDate; }
  public Date getBirthDate() { return this.birthDate; }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null) return false;
    if (getClass() != obj.getClass()) return false;
    PersonDTOV2 p = (PersonDTOV2) obj;
    return id == p.getId() && 
      Objects.equals(firstName, p.getFirstName()) &&
      Objects.equals(lastName, p.getLastName()) &&
      Objects.equals(address, p.getAddress()) &&
      Objects.equals(gender, p.getGender()) &&
      Objects.equals(birthDate, p.getBirthDate());
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, firstName, lastName, address, gender, birthDate);
  }

  @Override
  public String toString() {
    return "Person [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", address=" + address + ", gender="
        + gender + "birthDate=" + birthDate + "]";
  }
}
