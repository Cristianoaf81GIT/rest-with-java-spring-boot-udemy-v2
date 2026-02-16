package br.com.cristianoaf81.services.person;

import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Logger;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
//import org.springframework.data.web.PagedResourcesAssembler;
//import org.springframework.hateoas.PagedModel;

import br.com.cristianoaf81.exception.RequiredObjectIsNullException;
import br.com.cristianoaf81.exception.ResourceNotFoundException;
import br.com.cristianoaf81.mapper.custom.PersonMapper;

import static br.com.cristianoaf81.mapper.ObjectMapper.parseObject;
import br.com.cristianoaf81.model.Person;
import br.com.cristianoaf81.repository.PersonRepository;
import jakarta.transaction.Transactional;
import br.com.cristianoaf81.controller.PersonController;
import br.com.cristianoaf81.dto.v1.PersonDTO;
import br.com.cristianoaf81.dto.v2.PersonDTOV2;

@Service
public class PersonService {
    
  private AtomicLong counter = new AtomicLong();

  private Logger logger = Logger.getLogger(PersonService.class.getName());

  @Autowired
  private PersonRepository repository;

  @Autowired
  private PersonMapper converter;


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
    var dto = parseObject(repository
      .findById(id)
      .orElseThrow(() -> new ResourceNotFoundException(errMsg)), PersonDTO.class);
    dto.add(
      linkTo(
        methodOn(PersonController.class).findById(id)
      ).withSelfRel().withType("GET"));
    dto.setId(id);
    addHateosLinks(dto);
    return dto;
  } 

  public Page<PersonDTO> findAll(Pageable pageable) {
    logger.info("Finding all people");

    var people = repository.findAll(pageable);
    var peopleWithLinks = people.map(person -> {
      var dto = parseObject(person, PersonDTO.class);
      addHateosLinks(dto);
      return dto;
    });
    return peopleWithLinks;
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
    if (person == null) throw new RequiredObjectIsNullException();
    logger.info("Creating one person");
    Person p = parseObject(person, Person.class);
    PersonDTO dto = parseObject(repository.save(p), PersonDTO.class);
    addHateosLinks(dto);
    return dto;
  }


  public PersonDTOV2 createV2(PersonDTOV2 person) {
    if (person == null) throw new RequiredObjectIsNullException();
    logger.info("Creating one personV2" + person);
    Person p = converter.convertDTOtoEntity(person);
    logger.info("[person] " + p);
    return converter.convertEntityToDTO(repository.save(p));
  }


  public PersonDTO update(PersonDTO person) {
    if (person == null) throw new RequiredObjectIsNullException();
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
    
    PersonDTO dto = parseObject(repository.save(existingPerson), PersonDTO.class);
    addHateosLinks(dto);
    return dto;
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
 
  @Transactional // necessario quando implementamos um metodo nao default jpq no repository 
  public PersonDTO disablePerson(Long id) {
    logger.info(String.format("Disabling person with id %s", id));
    String errMsg = String.format("No record found for this person id: [%s]", id);
    repository
      .findById(id)
      .orElseThrow(() -> new ResourceNotFoundException(errMsg));

    repository.disablePerson(id); // metodo nao default implementado no repository do jpa
    var entity = repository.findById(id).get();
    var dto = parseObject(entity, PersonDTO.class);
    addHateosLinks(dto);
    return dto;
  }


  public void addHateosLinks(PersonDTO dto) {
    dto.add(
      linkTo(
        methodOn(PersonController.class).findById(dto.getId())
      ).withSelfRel().withType("GET"));

    dto.add(
      linkTo(
        methodOn(PersonController.class).findAll(1,12, "asc")
      ).withRel("findAll").withType("GET"));

    dto.add(
      linkTo(
        methodOn(PersonController.class).delete(dto.getId())
      ).withRel("delete").withType("DELETE"));

  
    dto.add(
      linkTo(
        methodOn(PersonController.class).create(dto)
      ).withRel("create").withType("POST"));

    dto.add(
      linkTo(
        methodOn(PersonController.class).update(dto)
      ).withRel("update").withType("PUT"));

    dto.add(
      linkTo(
        methodOn(PersonController.class).disablePerson(dto.getId())
      ).withRel("disable").withType("PATCH")
    );
  }
}
