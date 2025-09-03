package br.com.cristianoaf81.services.person;

import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Logger;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.cristianoaf81.exception.ResourceNotFoundException;
import br.com.cristianoaf81.model.Person;
import br.com.cristianoaf81.repository.PersonRepository;

@Service
public class PersonService {
    
  private AtomicLong counter = new AtomicLong();

  private Logger logger = Logger.getLogger(PersonService.class.getName());

  @Autowired
  private PersonRepository repository;

  List<Person> persons = new ArrayList<Person>();  

  public PersonService() {
    for (int i = 0; i < 8; i++) {
      Person p = mockPerson(i);
      persons.add(p);
    }
  }

  public Person findById(Long id) {
    logger.info("Finding one person!");
    String errMsg = String.format("No record found for this id: [%s]", id);
    return repository
      .findById(id)
      .orElseThrow(() -> new ResourceNotFoundException(errMsg));
  } 

  public List<Person> findAll() {
    logger.info("Finding all people");
    return repository.findAll();
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

  public Person create(Person person) {
    logger.info("Creating one person");
    return repository.save(person);
  }

  public Person update(Person person) {
    logger.info("Updating one person");
    String errMsg = String.format("No record found for this person id: [%s]", person.getId());
    
    Person existingPerson = repository
      .findById(person.getId())
      .orElseThrow(() -> new ResourceNotFoundException(errMsg));
   
    if (person.getGender() != null && person.getGender().length() > 0)
      existingPerson.setGender(person.getGender());
    if (person.getAddress() != null && person.getAddress().length() > 0)
      existingPerson.setAddress(person.getAddress());
    if (person.getLastName() != null && person.getLastName().length() > 0)
      existingPerson.setLastName(person.getLastName());
    if (person.getFirstName() != null && person.getLastName().length() > 0)
      existingPerson.setFirstName(person.getFirstName());
    
    return repository.save(existingPerson);
  }

  public void delete(Long id) {
    logger.info(String.format("Deleting person with id %s", id));
    String errMsg = String.format("No record found for this person id: [%s]", id);
    Person existingPerson = repository
      .findById(id)
      .orElseThrow(() -> new ResourceNotFoundException(errMsg));

    if (existingPerson != null)
      repository.delete(existingPerson);
  }

}
