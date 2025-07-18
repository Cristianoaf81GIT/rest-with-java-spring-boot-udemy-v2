package br.com.cristianoaf81.services.person;

import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Logger;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import br.com.cristianoaf81.model.Person;

@Service
public class PersonService {
    
  private AtomicLong counter = new AtomicLong();

  private Logger logger = Logger.getLogger(PersonService.class.getName());

  List<Person> persons = new ArrayList<Person>();  

  public PersonService() {
    for (int i = 0; i < 8; i++) {
      Person p = mockPerson(i);
      persons.add(p);
    }
  }

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

  public List<Person> findAll() {
    return persons;
  }

  private Person mockPerson(int i) {
    Person person = new Person();
    person.setId(counter.incrementAndGet());
    person.setFirstName(String.format("PERSON_NAME_%d", i+1));
    person.setLastName(String.format("PERSON_LAST_NAME_%d", i+1));
    person.setAddress(String.format("ADDRESS_%d", i+1));
    String gender = i % 2 == 0 ? "MALE" : "FEMALE";
    person.setGender(gender);
    return person;
  }

}
