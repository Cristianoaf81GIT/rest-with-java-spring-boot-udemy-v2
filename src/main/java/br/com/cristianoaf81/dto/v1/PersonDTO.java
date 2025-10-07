package br.com.cristianoaf81.dto.v1;

import java.io.Serializable;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import br.com.cristianoaf81.serializer.GenderSerializer;

import java.util.Date;

//import com.fasterxml.jackson.annotation.JsonIgnore;
//import com.fasterxml.jackson.annotation.JsonProperty;
//import com.fasterxml.jackson.annotation.JsonPropertyOrder;

//@JsonPropertyOrder({"id", "address", "first_name", "last_name", "gender"})
@JsonFilter("PersonFilter")
public class PersonDTO implements Serializable {
  
  private static final long serialVersionUID = -2878128062746201406L;

  private Long id;
  
  //@JsonProperty("first_name")
  private String firstName;
  
  //@JsonProperty("last_name")
  @JsonInclude(JsonInclude.Include.NON_NULL)
  private String lastName;

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  private String phoneNumber;
 
  //formata em pt-br
  @JsonFormat(pattern = "dd/MM/yyyy")
  private Date birthDay;

  private String address;

  //@JsonIgnore
  @JsonSerialize(using = GenderSerializer.class)
  private String gender;

  private String sensitiveData;

  public PersonDTO() {}

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

  // birthday
  public void setBirthDay(Date birthDay) { this.birthDay = birthDay; }
  public Date getBirthDay() { return this.birthDay; }

  // phoneNumber
  public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
  public String getPhoneNumber() { return this.phoneNumber; }

  // sensitiveData
  public void setSensitiveData(String sensitiveData) { this.sensitiveData = sensitiveData; }
  public String getSensitiveData() { return this.sensitiveData; }


  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null) return false;
    if (getClass() != obj.getClass()) return false;
    PersonDTO p = (PersonDTO) obj;
    return id == p.getId() && 
      Objects.equals(firstName, p.getFirstName()) &&
      Objects.equals(lastName, p.getLastName()) &&
      Objects.equals(address, p.getAddress()) &&
      Objects.equals(gender, p.getGender()) &&
      Objects.equals(birthDay, p.getBirthDay()) &&
      Objects.equals(phoneNumber, p.getPhoneNumber()) &&
      Objects.equals(sensitiveData, p.getSensitiveData());
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, firstName, lastName, address, gender, birthDay, phoneNumber, sensitiveData);
  }

  @Override
  public String toString() {
    return "Person [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", address=" + address + ", gender="
        + gender + "birthDay=" + birthDay + "phoneNumber=" + phoneNumber +"]";
  }
}
