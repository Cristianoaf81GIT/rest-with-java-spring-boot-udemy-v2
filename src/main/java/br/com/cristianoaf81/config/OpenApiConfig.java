package br.com.cristianoaf81.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration
public class OpenApiConfig {

  @Bean
  OpenAPI customOpenApi() {
    return new OpenAPI()
      .info(
        new Info()
          .title("Rest API's RESTful from 0 with java, springboot, Kubernets and Docker")
          .version("v1")
          .description("Curso Api Restful com java e springboot")
          .termsOfService("https://www.gnu.org/licenses/gpl-3.0.html")
          .license(
            new License()
             .name("gpl-3")
             .url("https://www.gnu.org/licenses/gpl-3.0.html")
          )
      );
  }
}
