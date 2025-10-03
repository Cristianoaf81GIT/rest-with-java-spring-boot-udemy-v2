package br.com.cristianoaf81.mapper.custom;

import java.util.Date;

import org.springframework.stereotype.Service;

import br.com.cristianoaf81.dto.v2.PersonDTOV2;
import br.com.cristianoaf81.model.Person;

@Service
public class PersonMapper {


  public PersonDTOV2 convertEntityToDTO(Person person) {
    PersonDTOV2 dto = new PersonDTOV2();
    dto.setId(person.getId() != null && person.getId() > 0 ? person.getId() : null);
    dto.setFirstName(person.getFirstName());
    dto.setLastName(person.getFirstName());
    dto.setAddress(person.getAddress());
    dto.setBirthDate(new Date());
    dto.setGender(person.getGender());
    return dto;
  }

  public Person convertDTOtoEntity(PersonDTOV2 dto) {
    Person person = new Person();
    person.setId(dto.getId() != null && dto.getId() > 0 ? dto.getId() : null);
    person.setFirstName(dto.getFirstName());
    person.setLastName(dto.getFirstName());
    person.setAddress(dto.getAddress());
    person.setGender(dto.getGender());
    return person;
  }

}
