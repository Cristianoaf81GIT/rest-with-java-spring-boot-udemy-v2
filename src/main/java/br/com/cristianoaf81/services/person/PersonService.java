package br.com.cristianoaf81.services.person;

import java.util.concurrent.atomic.AtomicLong;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.stream.Collectors;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.hateoas.Link;
import org.springframework.data.domain.Page;

import br.com.cristianoaf81.exception.BadRequestException;
import br.com.cristianoaf81.exception.FileStorageException;
import br.com.cristianoaf81.exception.RequiredObjectIsNullException;
import br.com.cristianoaf81.exception.ResourceNotFoundException;
import br.com.cristianoaf81.file.importer.contract.FileImporter;
import br.com.cristianoaf81.file.importer.factory.FileImporterFactory;
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

  private Logger logger = LoggerFactory.getLogger(getClass());

  @Autowired
  private PersonRepository repository;

  @Autowired
  private PersonMapper converter;

  @Autowired
  private PagedResourcesAssembler<PersonDTO> assembler;

  @Autowired
  private FileImporterFactory fileImporterFactory;


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
 
  public PagedModel<EntityModel<PersonDTO>> findAll(Pageable pageable) {
    logger.info("Finding all people");

    var people = repository.findAll(pageable);
    return buildPagedModel(pageable, people);  
  }
  
  @Transactional
  public PagedModel<EntityModel<PersonDTO>> findByName(String firstName, Pageable pageable) {
    logger.info("Finding by name");

    var people = repository.findPeopleByName(firstName,pageable);
    return buildPagedModel(pageable, people);  
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
        methodOn(PersonController.class).findByName("",1,12,"asc")
      ).withRel("findByName").withType("GET"));


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
        linkTo(methodOn(PersonController.class).massCreation(null))
        .withRel("massCreation")
        .withType("POST")
    );

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

  private PagedModel<EntityModel<PersonDTO>> buildPagedModel(Pageable pageable, Page<Person> people) {

    var peopleWithLinks = people.map(p -> {
      var dto = parseObject(p, PersonDTO.class);
      addHateosLinks(dto);
      return dto;
    });
    
    Link findAllLink = WebMvcLinkBuilder
    .linkTo(WebMvcLinkBuilder.methodOn(PersonController.class)
      .findAll(pageable.getPageNumber(), pageable.getPageSize(), String.valueOf(pageable.getSort()))).withSelfRel();

    return assembler.toModel(peopleWithLinks,findAllLink);
  }

  public List<PersonDTO> massCreation(MultipartFile file) {
    logger.info("Importing people from file!");

    if (file.isEmpty()) throw new BadRequestException("Please set a valid file!");

    try(InputStream is = file.getInputStream()) {
      String fileName = Optional.ofNullable(file.getOriginalFilename())
      .orElseThrow(() -> new BadRequestException("File name cannot be null"));

      FileImporter importer = this.fileImporterFactory.getImporter(fileName);
      List<Person> entities = importer.importFile(is)
        .stream()
        .map((PersonDTO dto) -> repository.save(parseObject(dto, Person.class)))
        .collect(Collectors.toList());


      var peopleWithLinks = entities.stream().map(p -> {
        var dto = parseObject(p, PersonDTO.class);
        addHateosLinks(dto);
        return dto;
      });
      
      return peopleWithLinks.toList();
    } catch (Exception e) {
      String message = String.format("Error while trying to import file [%s]", e);
      logger.error(message);
      throw new FileStorageException("Error processing file!");
    }

  }

}
