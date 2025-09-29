package br.com.cristianoaf81.services.person;

import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Logger;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.cristianoaf81.exception.ResourceNotFoundException;
import static br.com.cristianoaf81.mapper.ObjectMapper.parseObject;
import static br.com.cristianoaf81.mapper.ObjectMapper.parseListObjects;
import br.com.cristianoaf81.model.Person;
import br.com.cristianoaf81.repository.PersonRepository;
import br.com.cristianoaf81.dto.PersonDTO;

@Service
public class PersonService {
    
  private AtomicLong counter = new AtomicLong();

  private Logger logger = Logger.getLogger(PersonService.class.getName());

  @Autowired
  private PersonRepository repository;

  List<PersonDTO> persons = new ArrayList<PersonDTO>();  

  public PersonService() {
    for (int i = 0; i < 8; i++) {
      Person p = mockPerson(i);
      PersonDTO dto = parseObject(p, PersonDTO.class);
      persons.add(dto);
    }
  }

  public PersonDTO findById(Long id) {
    logger.info("Finding one person!");
    String errMsg = String.format("No record found for this id: [%s]", id);
    return parseObject(repository
      .findById(id)
      .orElseThrow(() -> new ResourceNotFoundException(errMsg)), PersonDTO.class);
  } 

  public List<PersonDTO> findAll() {
    logger.info("Finding all people");
    return parseListObjects(repository.findAll(),PersonDTO.class);
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

  public PersonDTO create(PersonDTO person) {
    logger.info("Creating one person");
    Person p = parseObject(person, Person.class);
    return parseObject(repository.save(p), PersonDTO.class);
  }

  public PersonDTO update(PersonDTO person) {
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
    
    return parseObject(repository.save(existingPerson), PersonDTO.class);
  }

  public ResponseEntity<?> delete(Long id) {
    logger.info(String.format("Deleting person with id %s", id));
    String errMsg = String.format("No record found for this person id: [%s]", id);
    Person existingPerson = repository
      .findById(id)
      .orElseThrow(() -> new ResourceNotFoundException(errMsg));

    if (existingPerson != null)
      repository.delete(existingPerson);

    return ResponseEntity.noContent().build();
  }

}
