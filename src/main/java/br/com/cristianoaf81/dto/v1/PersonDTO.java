package br.com.cristianoaf81.dto.v1;

import java.io.Serializable;
import java.util.Objects;

import org.springframework.hateoas.RepresentationModel;

//import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
//@JsonIgnoreProperties({"links"})
public class PersonDTO extends RepresentationModel<PersonDTO> implements Serializable {
  
  private static final long serialVersionUID = 7965371053387682674L;
 
  private Long id;

  private String firstName;

  private String lastName;

  private String address;

  private String gender;

  private Boolean enabled;

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

  // enabled
  public void setEnabled(Boolean enabled) { this.enabled = enabled; }
  public Boolean getEnabled() { return this.enabled; }

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
      Objects.equals(enabled, p.getEnabled());
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, firstName, lastName, address, gender, enabled);
  }

  @Override
  public String toString() {
    return "Person [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", address=" + address + ", gender="
        + gender +", enabled=" + enabled +"]";
  }
}
