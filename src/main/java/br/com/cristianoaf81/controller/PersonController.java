package br.com.cristianoaf81.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import br.com.cristianoaf81.controller.docs.PersonApiDocInterface;
import br.com.cristianoaf81.dto.v1.PersonDTO;
import br.com.cristianoaf81.dto.v2.PersonDTOV2;
import br.com.cristianoaf81.file.exporter.MediaTypes;
import br.com.cristianoaf81.services.person.PersonService;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/person/v1")
public class PersonController implements PersonApiDocInterface {

  public PersonService getPersonService() {
    return personService;
  }

  @Autowired
  private PersonService personService;

  @GetMapping(value = "/{id}", produces = {
      MediaType.APPLICATION_JSON_VALUE,
      MediaType.APPLICATION_XML_VALUE,
      MediaType.APPLICATION_YAML_VALUE
  })
  @Override
  public PersonDTO findById(@PathVariable(name = "id") Long id) {
    return personService.findById(id);
  }

  @GetMapping(produces = {
      MediaType.APPLICATION_JSON_VALUE,
      MediaType.APPLICATION_XML_VALUE,
      MediaType.APPLICATION_YAML_VALUE
  })
  @Override
  public ResponseEntity<PagedModel<EntityModel<PersonDTO>>> findAll(
      @RequestParam(name = "page", defaultValue = "0") Integer page,
      @RequestParam(name = "size", defaultValue = "12") Integer size,
      @RequestParam(name = "direction", defaultValue = "asc") String direction) {
    Direction sortDirection = "desc".equalsIgnoreCase(direction) ? Direction.DESC : Direction.ASC;
    Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, "firstName"));
    return ResponseEntity.ok(personService.findAll(pageable));
  }

  @PostMapping(produces = {
      MediaType.APPLICATION_JSON_VALUE,
      MediaType.APPLICATION_XML_VALUE,
      MediaType.APPLICATION_YAML_VALUE
  }, consumes = {
      MediaType.APPLICATION_JSON_VALUE,
      MediaType.APPLICATION_YAML_VALUE,
      MediaType.APPLICATION_XML_VALUE
  })
  @Override
  public PersonDTO create(@RequestBody PersonDTO person) {
    return personService.create(person);
  }

  @PostMapping(value = "/v2", produces = {
      MediaType.APPLICATION_JSON_VALUE,
      MediaType.APPLICATION_XML_VALUE,
      MediaType.APPLICATION_YAML_VALUE
  }, consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_YAML_VALUE })
  public PersonDTOV2 createV2(@RequestBody PersonDTOV2 person) {
    return personService.createV2(person);
  }

  @PutMapping(produces = {
      MediaType.APPLICATION_JSON_VALUE,
      MediaType.APPLICATION_XML_VALUE,
      MediaType.APPLICATION_YAML_VALUE
  }, consumes = {
      MediaType.APPLICATION_JSON_VALUE,
      MediaType.APPLICATION_XML_VALUE,
      MediaType.APPLICATION_YAML_VALUE
  })
  @Override
  public PersonDTO update(@RequestBody PersonDTO person) {
    return personService.update(person);
  }

  @DeleteMapping(value = "/{id}")
  @Override
  public ResponseEntity<?> delete(@PathVariable(name = "id") Long id) {
    return personService.delete(id);
  }

  @PatchMapping(value = "/{id}", produces = {
      MediaType.APPLICATION_JSON_VALUE,
      MediaType.APPLICATION_XML_VALUE,
      MediaType.APPLICATION_YAML_VALUE
  })
  @Override
  public PersonDTO disablePerson(@PathVariable(name = "id") Long id) {
    return personService.disablePerson(id);
  }

  @GetMapping(value = "/findPeopleByName/{firstName}", produces = {
      MediaType.APPLICATION_JSON_VALUE,
      MediaType.APPLICATION_XML_VALUE,
      MediaType.APPLICATION_YAML_VALUE
  })
  @Override
  public ResponseEntity<PagedModel<EntityModel<PersonDTO>>> findByName(
      @PathVariable(name = "firstName") String firstName,
      @RequestParam(name = "page", defaultValue = "0") Integer page,
      @RequestParam(name = "size", defaultValue = "12") Integer size,
      @RequestParam(name = "direction", defaultValue = "asc") String direction) {
    Direction sortDirection = "desc".equalsIgnoreCase(direction) ? Direction.DESC : Direction.ASC;
    Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, "firstName"));

    return ResponseEntity.ok(personService.findByName(firstName, pageable));
  }

  @PostMapping(value = "/massCreation", produces = {
      MediaType.APPLICATION_JSON_VALUE,
      MediaType.APPLICATION_XML_VALUE,
      MediaType.APPLICATION_YAML_VALUE
  })
  @Override
  public List<PersonDTO> massCreation(@RequestParam(name = "file") MultipartFile file) {
    return personService.massCreation(file);
  }

  @GetMapping(value = "/exportPage", produces = {
      MediaTypes.APPLICATION_CSV_VALUE,
      MediaTypes.APPLICATION_XLSX_VALUE
  })
  @Override
  public ResponseEntity<Resource> exportPage(
      @RequestParam(name = "page", defaultValue = "0") Integer page,
      @RequestParam(name = "size", defaultValue = "12") Integer size,
      @RequestParam(name = "direction", defaultValue = "asc") String direction,
      HttpServletRequest request) {

    Direction sortDirection = "desc".equalsIgnoreCase(direction) ? Direction.DESC : Direction.ASC;
    Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, "firstName"));
    String acceptHeader = request.getHeader(HttpHeaders.ACCEPT);
    Resource file = personService.exportPage(pageable, acceptHeader);
    String contentType = acceptHeader != null ? acceptHeader : "application/octet-stream";

    var fileExtension = MediaTypes.APPLICATION_XLSX_VALUE.equalsIgnoreCase(acceptHeader) ? ".xlsx" : ".csv";
    var fileName = "people_exported" + fileExtension;

    return ResponseEntity.ok()
        .contentType(MediaType.parseMediaType(contentType))
        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
        .body(file);
  }
}
