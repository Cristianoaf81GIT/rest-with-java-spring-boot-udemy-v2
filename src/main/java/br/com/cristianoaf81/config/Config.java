package br.com.cristianoaf81.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import br.com.cristianoaf81.services.person.PersonService;

@Configuration
@ComponentScan("br.com.cristianoaf81.services")
public class Config {

  @Bean
  public PersonService personService() {
    return new PersonService(); 
  }
}
