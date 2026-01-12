package br.com.cristianoaf81.config;

import org.springframework.beans.factory.annotation.Value;

// import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.http.MediaType;
// import org.springframework.http.converter.HttpMessageConverter;

import br.com.cristianoaf81.serialization.converter.YamlJackson2HttpMessageConverter;

@Configuration
public class WebConfig implements WebMvcConfigurer {
  
  // le os valores de cors de dentro do application properties
  @Value("${cors.originPatterns}") 
  private String corsOriginPatterns = "";


  @Override
  public void addCorsMappings(CorsRegistry registry) {
    // recebe os valores separados por virgula e converte em um array de strings
    var allowedOrigins = corsOriginPatterns.split(",");
    System.out.println(allowedOrigins);
    // adiciona no registro de cors as origens seguindo o wildcard **, ou seja, todas as rotas da aplicacao
    registry.addMapping("/**")
      .allowedOrigins(allowedOrigins)
      //.allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
      .allowedMethods("*") // libera todos os metodos
      .allowCredentials(true); // permite o uso de credenciais
  }

  @Override
  public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
    
//    configurer
//      .favorParameter(true)
//      .parameterName("mediaType")
//      .ignoreAcceptHeader(true)
//      .useRegisteredExtensionsOnly(false)
//      .defaultContentType(MediaType.APPLICATION_JSON)
//      .mediaType("json", MediaType.APPLICATION_JSON)
//      .mediaType("xml",MediaType.APPLICATION_XML);


    configurer
      .favorParameter(false)
      .ignoreAcceptHeader(false)
      .useRegisteredExtensionsOnly(false)
      .defaultContentType(MediaType.APPLICATION_JSON)
      .mediaType("json", MediaType.APPLICATION_JSON)
      .mediaType("xml",MediaType.APPLICATION_XML)
      .mediaType("yaml", YamlJackson2HttpMessageConverter.MEDIA_TYPE)
      .mediaType("yml", YamlJackson2HttpMessageConverter.ALT_MEDIA_TYPE);

   // WebMvcConfigurer.super.configureContentNegotiation(configurer);
  }

// YAML not need to load on spring 3 
// Need to add at pom.xml 
/*
 *     <dependency>
      <groupId>com.fasterxml.jackson.dataformat</groupId>
      <artifactId>jackson-dataformat-yaml</artifactId>
    </dependency>

 * */
//  @Override
//  public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
//    converters.add(0, new YamlJackson2HttpMessageConverter()); // adiciona suporte para yaml
//    converters.forEach(c -> System.out.println("Converter registrado: " + c.getClass().getName()));
//    // WebMvcConfigurer.super.extendMessageConverters(converters);
//  }
}
