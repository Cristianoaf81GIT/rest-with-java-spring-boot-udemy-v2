package br.com.cristianoaf81.services.person;

import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Logger;

import org.springframework.stereotype.Service;

import br.com.cristianoaf81.model.Person;

@Service
public class PersonService {
    
  private AtomicLong counter = new AtomicLong();

  private Logger logger = Logger.getLogger(PersonService.class.getName());
  
  public Person findById(String id) {
    logger.info("Finding one person!");
    Person person = new Person();
    person.setId(counter.incrementAndGet());
    person.setFirstName("Leandro");
    person.setLastName("Costa");
    person.setAddress("Uberlândia - Minas Gerais, Brasil");
    person.setGender("male");
    return person;
  }  

}
