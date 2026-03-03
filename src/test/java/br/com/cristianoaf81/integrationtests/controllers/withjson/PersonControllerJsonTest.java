package br.com.cristianoaf81.integrationtests.controllers.withjson;

import java.util.List;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;

import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.cristianoaf81.config.TestConfigs;
import br.com.cristianoaf81.unittests.dto.PersonDTO;
//import br.com.cristianoaf81.dto.v1.PersonDTO;
import br.com.cristianoaf81.integrationtests.testcontainers.AbstractIntegrationTest;
import br.com.cristianoaf81.unittests.dto.wrapper.WrapperPersonDTO;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeAll;


@SpringBootTest(
  webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT,
  properties = {
    "server.port=8888",
    "cors.originPatterns: http://localhost:8080,https://www.google.com.br,http://localhost:3000,http://www.google.com.br"
  }
)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ActiveProfiles("test")
public class PersonControllerJsonTest extends AbstractIntegrationTest {
  
  private static RequestSpecification specification;
  private static ObjectMapper objectMapper;
  private static PersonDTO person;

  @BeforeAll
  static void setup() {
    objectMapper = new ObjectMapper();
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
    .setPort(TestConfigs.SERVER_PORT)
    .addFilter(new RequestLoggingFilter(LogDetail.ALL))
    .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
    .build();

    var content = given(specification)
    .contentType(MediaType.APPLICATION_JSON_VALUE)
    .body(person)
    .when()
    .post()
    .then()
    .statusCode(200)
    .contentType(MediaType.APPLICATION_JSON_VALUE)
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
    //mockPerson();
//    specification = new RequestSpecBuilder()
//    .addHeader(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_GOOGLE)
//    .setBasePath("/api/person/v1")
//    .setPort(TestConfigs.SERVER_PORT)
//    .addFilter(new RequestLoggingFilter(LogDetail.ALL))
//    .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
//    .build();

    var content = given(specification)
    .contentType(MediaType.APPLICATION_JSON_VALUE)
    .pathParam("id", person.getId())
    .when()
    .get("{id}")
    .then()
    .statusCode(200)
    .contentType(MediaType.APPLICATION_JSON_VALUE)
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
    .setPort(TestConfigs.SERVER_PORT)
    .addFilter(new RequestLoggingFilter(LogDetail.ALL))
    .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
    .build();

    var content = given(specification)
    .contentType(MediaType.APPLICATION_JSON_VALUE)
    .body(person)
    .when()
    .put()
    .then()
    .statusCode(200)
    .contentType(MediaType.APPLICATION_JSON_VALUE)
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
    .contentType(MediaType.APPLICATION_JSON_VALUE)
    .pathParam("id", person.getId())
    .when()
    .patch("{id}")
    .then()
    .statusCode(200)
    .contentType(MediaType.APPLICATION_JSON_VALUE)
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
    .accept(MediaType.APPLICATION_JSON_VALUE)
    .when()
    .get()
    .then()
    .statusCode(200)
    .contentType(MediaType.APPLICATION_JSON_VALUE)
    .extract()
    .body()
    .asString();

    WrapperPersonDTO wrapper = objectMapper.readValue(content, WrapperPersonDTO.class);
    List<PersonDTO> people = wrapper.getEmbedded().getPeople();

    PersonDTO personOne = people.get(0);
    person = personOne;

    assertNotNull(personOne.getId());
    assertTrue(personOne.getId() > 0);
    assertNotNull(personOne.getFirstName());
    assertNotNull(personOne.getLastName());
    assertNotNull(personOne.getAddress());
    assertNotNull(personOne.getGender());
    assertTrue(personOne.getEnabled());

    assertEquals("Ayrton",personOne.getFirstName());
    assertEquals("Senna",personOne.getLastName());
    assertEquals("São Paulo, Brazil",personOne.getAddress());
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

    assertEquals("Muhamad",personFour.getFirstName());
    assertEquals("Ali",personFour.getLastName());
    assertEquals("Kentuck - US",personFour.getAddress());
    assertEquals("Male",personFour.getGender());
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
