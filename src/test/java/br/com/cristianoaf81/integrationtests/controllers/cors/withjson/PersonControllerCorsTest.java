package br.com.cristianoaf81.integrationtests.controllers.cors.withjson;

// import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.cristianoaf81.config.TestConfigs;
import br.com.cristianoaf81.dto.v1.PersonDTO;
import br.com.cristianoaf81.integrationtests.testcontainers.AbstractIntegrationTest;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeAll;


@SpringBootTest(
  webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT,
  properties = {
    //"server.port=8888",
    "cors.originPatterns: http://localhost:8080,https://www.google.com.br,http://localhost:3000,http://www.google.com.br"
  }
)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ActiveProfiles("test")
public class PersonControllerCorsTest extends AbstractIntegrationTest {
  
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


    assertEquals("Richard",createdPerson.getFirstName());
    assertEquals("Stall",createdPerson.getLastName());
    assertEquals("New York City - EUA",createdPerson.getAddress());
    assertEquals("Male",createdPerson.getGender());
    assertTrue(createdPerson.getEnabled());
 }

  @Test
  @Order(2)
  void createWithWrongOrigin() throws JsonProcessingException {
    mockPerson();
    specification = new RequestSpecBuilder()
    .addHeader(TestConfigs.HEADER_PARAM_ORIGIN, "https://www.semeru.com.br")
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
    .statusCode(403)
    .extract()
    .body()
    .asString();
    assertEquals("Invalid CORS request", content);
 }


  @Test
  @Order(3)
  void findById() throws JsonProcessingException {
    //mockPerson();
    specification = new RequestSpecBuilder()
    .addHeader(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_GOOGLE)
    .setBasePath("/api/person/v1")
    .setPort(TestConfigs.SERVER_PORT)
    .addFilter(new RequestLoggingFilter(LogDetail.ALL))
    .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
    .build();

    var content = given(specification)
    .contentType(MediaType.APPLICATION_JSON_VALUE)
    .pathParam("id", person.getId())
    .when()
    .get("{id}")
    .then()
    .statusCode(200)
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

    assertEquals("Richard",createdPerson.getFirstName());
    assertEquals("Stall",createdPerson.getLastName());
    assertEquals("New York City - EUA",createdPerson.getAddress());
    assertEquals("Male",createdPerson.getGender());

  }

  @Test
  @Order(4)
  void findByIdWithWrongOrigin() throws JsonProcessingException {
    //mockPerson();
    specification = new RequestSpecBuilder()
    .addHeader(TestConfigs.HEADER_PARAM_ORIGIN, "http://www.testedevelocidade.com.br")
    .setBasePath("/api/person/v1")
    .setPort(TestConfigs.SERVER_PORT)
    .addFilter(new RequestLoggingFilter(LogDetail.ALL))
    .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
    .build();

    var content = given(specification)
    .contentType(MediaType.APPLICATION_JSON_VALUE)
    .pathParam("id", person.getId())
    .when()
    .get("{id}")
    .then()
    .statusCode(403)
    .extract()
    .body()
    .asString();

    assertEquals("Invalid CORS request", content);
  }


  @Test
  void update() {}

  @Test
  void delete() {}

  @Test
  void findAll() {}

  private void mockPerson() {
    person.setFirstName("Richard");
    person.setLastName("Stall");
    person.setAddress("New York City - EUA");
    person.setGender("Male");
    person.setEnabled(true);
  }
}
