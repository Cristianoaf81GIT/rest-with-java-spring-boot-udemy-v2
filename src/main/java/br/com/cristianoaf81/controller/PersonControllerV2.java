package br.com.cristianoaf81.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.MediaType;
import br.com.cristianoaf81.dto.v2.PersonDTOV2;
import br.com.cristianoaf81.services.person.PersonService;

@RestController
@RequestMapping("/api/person/v2")
public class PersonControllerV2 {

  @Autowired
  private PersonService personService;
   

  @PostMapping(
    produces = MediaType.APPLICATION_JSON_VALUE, 
    consumes = MediaType.APPLICATION_JSON_VALUE
  )
  public PersonDTOV2 createV2(@RequestBody PersonDTOV2 person) {
    return personService.createV2(person);
  }

}
