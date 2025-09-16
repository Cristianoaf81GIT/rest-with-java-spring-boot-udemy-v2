package br.com.cristianoaf81.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import br.com.cristianoaf81.model.Person;
import br.com.cristianoaf81.services.person.PersonService;

@RestController
@RequestMapping("/person")
public class PersonController {

  @Autowired
  private PersonService personService;
   
  // @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
  @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public Person findById(@PathVariable(name = "id") Long id) {
    return personService.findById(id);
  }
  
  // @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public List<Person> findAll() {
    return personService.findAll();
  }

  // @RequestMapping(method =  RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
  @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
  public Person create(@RequestBody Person person) {
    return personService.create(person);
  }

  // @RequestMapping(method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
  @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
  public Person update(@RequestBody Person person) {
    return personService.update(person);
  }

  // @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
  @DeleteMapping(value = "/{id}")
  public ResponseEntity<?> delete(@PathVariable(name="id") Long id) {
    return personService.delete(id);
  }
}
