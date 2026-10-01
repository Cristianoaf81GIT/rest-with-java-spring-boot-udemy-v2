package br.com.cristianoaf81.integrationtests.controllers.withjson;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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

import br.com.cristianoaf81.dto.security.AccountCredentialsDTO;
import br.com.cristianoaf81.dto.security.TokenDTO;
import br.com.cristianoaf81.integrationtests.testcontainers.AbstractIntegrationTest;
import io.restassured.specification.RequestSpecification;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ActiveProfiles("test")
@TestInstance(Lifecycle.PER_CLASS)
public class AuthControllerJsonTest extends AbstractIntegrationTest {

  @LocalServerPort
  private int serverPort;

  private static AccountCredentialsDTO credentials;
  private static TokenDTO token;
  private static RequestSpecification specification;

  @BeforeAll
  void setup() {
    credentials = new AccountCredentialsDTO();
    token = new TokenDTO();
  }

  @Test
  @Order(1)
  void signIn() {
    credentials.setUserName("leandro");
    credentials.setPassword("admin123");
    token = given()
        .basePath("/auth/signin")
        .port(serverPort)
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .body(credentials)
        .when()
        .post()
        .then()
        .statusCode(200)
        .extract()
        .body()
        .as(TokenDTO.class);

    assertNotNull(token.getAccessToken());
    assertNotNull(token.getRefreshToken());
  }

  @Test
  @Order(2)
  void signInWithRefreshToken() {
  }
}
