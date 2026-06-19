package br.com.cristianoaf81.integrationtests.controllers.withxml;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import br.com.cristianoaf81.config.TestConfigs;
import br.com.cristianoaf81.unittests.dto.PersonDTO;
import br.com.cristianoaf81.integrationtests.testcontainers.AbstractIntegrationTest;
import br.com.cristianoaf81.unittests.dto.wrapper.xml.PagedModelPerson;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;


@SpringBootTest(
  webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
  properties = {
    "cors.originPatterns: http://localhost:8080,https://www.google.com.br,http://localhost:3000,http://www.google.com.br"
  }
)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ActiveProfiles("test")
@TestInstance(Lifecycle.PER_CLASS)
public class PersonControllerXmlTest extends AbstractIntegrationTest {
  
  @LocalServerPort
  private int serverPort;

  private static RequestSpecification specification;
  private static XmlMapper objectMapper;
  private static PersonDTO person;

  @BeforeAll
  void setup() {
    objectMapper = new XmlMapper();
    objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    person = new PersonDTO();
  }

  @Test
  @Order(1)
  void create() throws JsonProcessingException {
    mockPerson();
    person.setLastName("Benedict Torvalds");
    specification = new RequestSpecBuilder()
    .addHeader(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_GOOGLE)
    .setBasePath("/api/person/v1")
    .setPort(serverPort)
    .addFilter(new RequestLoggingFilter(LogDetail.ALL))
    .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
    .build();

    var content = given(specification)
    .contentType(MediaType.APPLICATION_XML_VALUE)    
    .accept(MediaType.APPLICATION_XML_VALUE)
    .body(person)
    .when()
    .post()
    .then()
    .statusCode(200)
    .contentType(MediaType.APPLICATION_XML_VALUE)
    .extract()
    .body()
    .asString();

    PersonDTO createdPerson = objectMapper.readValue(content, PersonDTO.class);
    person = createdPerson;

    assertNotNull(createdPerson.getId());
    assertTrue(createdPerson.getId() > 0);
    assertNotNull(createdPerson.getFirstName());
    assertNotNull(createdPerson.getLastName());
    assertNotNull(createdPerson.getAddress());
    assertNotNull(createdPerson.getGender());


    assertEquals("Linus",createdPerson.getFirstName());
    assertEquals("Benedict Torvalds",createdPerson.getLastName());
    assertEquals("Helsink - FINLAND",createdPerson.getAddress());
    assertEquals("Male",createdPerson.getGender());
    assertTrue(createdPerson.getEnabled());
  }

  @Test
  @Order(2)
  void findById() throws JsonProcessingException {
    var content = given(specification)
    .contentType(MediaType.APPLICATION_XML_VALUE)    
    .accept(MediaType.APPLICATION_XML_VALUE)
    .pathParam("id", person.getId())
    .when()
    .get("{id}")
    .then()
    .statusCode(200)
    .contentType(MediaType.APPLICATION_XML_VALUE)
    .extract()
    .body()
    .asString();

    PersonDTO createdPerson = objectMapper.readValue(content, PersonDTO.class);
    person = createdPerson;

    assertNotNull(createdPerson.getId());
    assertTrue(createdPerson.getId() > 0);
    assertNotNull(createdPerson.getFirstName());
    assertNotNull(createdPerson.getLastName());
    assertNotNull(createdPerson.getAddress());
    assertNotNull(createdPerson.getGender());
    assertTrue(createdPerson.getEnabled());

    assertEquals("Linus",createdPerson.getFirstName());
    assertEquals("Benedict Torvalds",createdPerson.getLastName());
    assertEquals("Helsink - FINLAND",createdPerson.getAddress());
    assertEquals("Male",createdPerson.getGender());
    assertTrue(createdPerson.getEnabled());
  }

  @Test
  @Order(3)
  void update() throws JsonProcessingException {
    mockPerson();
    specification = new RequestSpecBuilder()
    .addHeader(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_GOOGLE)
    .setBasePath("/api/person/v1")
    .setPort(serverPort)
    .addFilter(new RequestLoggingFilter(LogDetail.ALL))
    .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
    .build();

    var content = given(specification)
    .contentType(MediaType.APPLICATION_XML_VALUE)
    .accept(MediaType.APPLICATION_XML_VALUE)
    .body(person)
    .when()
    .put()
    .then()
    .statusCode(200)
    .contentType(MediaType.APPLICATION_XML_VALUE)
    .extract()
    .body()
    .asString();

    PersonDTO createdPerson = objectMapper.readValue(content, PersonDTO.class);
    person = createdPerson;

    assertNotNull(createdPerson.getId());
    assertTrue(createdPerson.getId() > 0);
    assertNotNull(createdPerson.getFirstName());
    assertNotNull(createdPerson.getLastName());
    assertNotNull(createdPerson.getAddress());
    assertNotNull(createdPerson.getGender());


    assertEquals("Linus",createdPerson.getFirstName());
    assertEquals("Torvalds",createdPerson.getLastName());
    assertEquals("Helsink - FINLAND",createdPerson.getAddress());
    assertEquals("Male",createdPerson.getGender());
    assertTrue(createdPerson.getEnabled());

  }


  @Test
  @Order(4)
  void disable() throws JsonProcessingException {
    var content = given(specification)
    .contentType(MediaType.APPLICATION_XML_VALUE)
    .accept(MediaType.APPLICATION_XML_VALUE)
    .pathParam("id", person.getId())
    .when()
    .patch("{id}")
    .then()
    .statusCode(200)
    .contentType(MediaType.APPLICATION_XML_VALUE)
    .extract()
    .body()
    .asString();

    PersonDTO createdPerson = objectMapper.readValue(content, PersonDTO.class);
    person = createdPerson;

    assertNotNull(createdPerson.getId());
    assertTrue(createdPerson.getId() > 0);
    assertNotNull(createdPerson.getFirstName());
    assertNotNull(createdPerson.getLastName());
    assertNotNull(createdPerson.getAddress());
    assertNotNull(createdPerson.getGender());
    assertFalse(createdPerson.getEnabled());
    createdPerson.setLastName("Benedict Torvalds");
    assertEquals("Linus",createdPerson.getFirstName());
    assertEquals("Benedict Torvalds",createdPerson.getLastName());
    assertEquals("Helsink - FINLAND",createdPerson.getAddress());
    assertEquals("Male",createdPerson.getGender());
    assertFalse(createdPerson.getEnabled());
  }

  @Test
  @Order(5)
  void delete() {
    given(specification)
    .accept(MediaType.APPLICATION_XML_VALUE)
    .pathParam("id", person.getId())
    .when()
    .delete("{id}")
    .then()
    .statusCode(204);    

  }

  @Test
  @Order(6)
  void findAll() throws JsonProcessingException {
    var content = given(specification)
    .accept(MediaType.APPLICATION_XML_VALUE)
    .queryParams("page", 3, "size", 12, "direction", "asc")
    .when()
    .get()
    .then()
    .statusCode(200)
    .contentType(MediaType.APPLICATION_XML_VALUE)
    .extract()
    .body()
    .asString();
    
    PagedModelPerson wrapper = objectMapper.readValue(content, PagedModelPerson.class);
    List<PersonDTO> people = wrapper.getContent();

     PersonDTO personOne = people.get(0);
    person = personOne;

    assertNotNull(personOne.getId());
    assertTrue(personOne.getId() > 0);
    assertNotNull(personOne.getFirstName());
    assertNotNull(personOne.getLastName());
    assertNotNull(personOne.getAddress());
    assertNotNull(personOne.getGender());
    assertTrue(personOne.getEnabled());

    assertEquals("Anderson",personOne.getFirstName());
    assertEquals("Blowen",personOne.getLastName());
    assertEquals("Room 973",personOne.getAddress());
    assertEquals("Male",personOne.getGender());
    assertTrue(personOne.getEnabled());

    var personFour = people.get(4);
    person = personFour;

    assertNotNull(personFour.getId());
    assertTrue(personFour.getId() > 0);
    assertNotNull(personFour.getFirstName());
    assertNotNull(personFour.getLastName());
    assertNotNull(personFour.getAddress());
    assertNotNull(personFour.getGender());
    assertTrue(personFour.getEnabled());

    /*
     *"firstName": "Anette",
                "lastName": "Gentery",
                "address": "Room 1192",
                "gender": "Female",
                "enabled": true,
     * */

    assertEquals("Anette",personFour.getFirstName());
    assertEquals("Gentery",personFour.getLastName());
    assertEquals("Room 1192",personFour.getAddress());
    assertEquals("Female",personFour.getGender());
    assertTrue(personFour.getEnabled());
 }

  private void mockPerson() {
    person.setFirstName("Linus");
    person.setLastName("Torvalds");
    person.setAddress("Helsink - FINLAND");
    person.setGender("Male");
    person.setEnabled(true);
  }
}
