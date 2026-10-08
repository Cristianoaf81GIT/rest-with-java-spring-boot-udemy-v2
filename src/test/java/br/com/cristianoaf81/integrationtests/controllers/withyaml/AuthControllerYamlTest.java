package br.com.cristianoaf81.integrationtests.controllers.withyaml;

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

import com.fasterxml.jackson.core.JsonProcessingException;

import br.com.cristianoaf81.config.TestConfigs;
import br.com.cristianoaf81.dto.security.AccountCredentialsDTO;
import br.com.cristianoaf81.dto.security.TokenDTO;
import br.com.cristianoaf81.integrationtests.controllers.withyaml.mapper.YamlMapper;
import br.com.cristianoaf81.integrationtests.testcontainers.AbstractIntegrationTest;
import io.restassured.config.EncoderConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.http.ContentType;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ActiveProfiles("test")
@TestInstance(Lifecycle.PER_CLASS)
public class AuthControllerYamlTest extends AbstractIntegrationTest {

  @LocalServerPort
  private int serverPort;

  private static AccountCredentialsDTO credentials;
  private static TokenDTO token;
  private static YamlMapper objectMapper;

  @BeforeAll
  void setup() {
    objectMapper = new YamlMapper();
    credentials = new AccountCredentialsDTO();
    token = new TokenDTO();
  }

  @Test
  @Order(1)
  void signIn() throws JsonProcessingException {
    credentials.setUserName("leandro");
    credentials.setPassword("admin123");
    token = given()
        .config(
            RestAssuredConfig
                .config()
                .encoderConfig(
                    EncoderConfig
                        .encoderConfig()
                        .encodeContentTypeAs(MediaType.APPLICATION_YAML_VALUE, ContentType.TEXT)))
        .basePath("/auth/signin")
        .port(serverPort)
        .contentType(MediaType.APPLICATION_YAML_VALUE)
        .accept(MediaType.APPLICATION_YAML_VALUE)
        .body(credentials, objectMapper)
        .when()
        .post()
        .then()
        .statusCode(200)
        .extract()
        .body()
        .as(TokenDTO.class, objectMapper);

    assertNotNull(token.getAccessToken());
    assertNotNull(token.getRefreshToken());
  }

  @Test
  @Order(2)
  void signInWithRefreshToken() throws JsonProcessingException {
    token = given()
        .config(
            RestAssuredConfig
                .config()
                .encoderConfig(
                    EncoderConfig
                        .encoderConfig()
                        .encodeContentTypeAs(MediaType.APPLICATION_YAML_VALUE, ContentType.TEXT)))
        .basePath("/auth/refresh")
        .port(serverPort)
        .contentType(MediaType.APPLICATION_YAML_VALUE)
        .accept(MediaType.APPLICATION_YAML_VALUE)
        .pathParam("userName", token.getUsername())
        .header(TestConfigs.HEADER_PARAM_AUTHORIZATION, "Bearer " + token.getRefreshToken())
        .when()
        .put("{userName}")
        .then()
        .statusCode(200)
        .extract()
        .body()
        .as(TokenDTO.class, objectMapper);
    assertNotNull(token.getAccessToken());
    assertNotNull(token.getRefreshToken());
  }
}
