package br.com.cristianoaf81.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.MediaType;
import br.com.cristianoaf81.model.Person;
import br.com.cristianoaf81.services.person.PersonService;

@RestController
@RequestMapping("/person")
public class PersonController {

  @Autowired
  private PersonService personService;
   
  @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
  public Person findById(@PathVariable(name = "id") String id) {
    return personService.findById(id);
  }
  
  @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
  public List<Person> findAll() {
    return personService.findAll();
  }
}
