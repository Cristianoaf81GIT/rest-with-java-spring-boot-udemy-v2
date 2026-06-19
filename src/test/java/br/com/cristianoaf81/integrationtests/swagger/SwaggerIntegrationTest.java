package br.com.cristianoaf81.integrationtests.swagger;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;

import static io.restassured.RestAssured.given;

import br.com.cristianoaf81.config.TestConfigs;
import br.com.cristianoaf81.integrationtests.testcontainers.AbstractIntegrationTest;

@SpringBootTest(
  webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@ActiveProfiles("test")
class SwaggerIntegrationTest extends AbstractIntegrationTest {

  @LocalServerPort
  private int serverPort;

	@Test
	void shouldDisplaySwaggerUiPage() {
    var content = given()
      .basePath("/swagger-ui/index.html")
      .port(serverPort)
      .when()
      .get()
      .then()
      .statusCode(200)
      .extract()
      .body()
      .asString();

    assertTrue(content.contains("Swagger UI"));
	}

}
