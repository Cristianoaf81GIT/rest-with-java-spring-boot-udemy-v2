package br.com.cristianoaf81.unittests.dto.wrapper.json;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

import br.com.cristianoaf81.unittests.dto.PersonDTO;

public class PersonEmbeddedDTO implements Serializable {

  private static final long serialVersionUID = 1L;

  @JsonProperty("people")
  private List<PersonDTO> people;

  public PersonEmbeddedDTO() {}

  public List<PersonDTO> getPeople() { return this.people; }
  public void setPeople(List<PersonDTO> people) { this.people = people; }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null || getClass() != obj.getClass()) return false;
    PersonEmbeddedDTO p = (PersonEmbeddedDTO) obj;
    return Objects.equals(people, p.people);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(people);
  }

  @Override
  public String toString() {
    return "PersonEmbeddedDTO [people=" + people + "]";
  }
}
