package br.com.cristianoaf81.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.com.cristianoaf81.integrationtests.testcontainers.AbstractIntegrationTest;
import br.com.cristianoaf81.model.Person;

@ExtendWith(SpringExtension.class) // permite carregar o contexto da applicacao especialmente beans
@DataJpaTest // configura a estrutura do teste para testar componentes jpa 
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // garante que o banco de dados real seja config para testes
@TestMethodOrder(MethodOrderer.OrderAnnotation.class) // define a ordem dos testes
@ActiveProfiles("test")
class PersonRepositoryTest extends AbstractIntegrationTest {

  @Autowired
  PersonRepository repository;
  private static Person person;

  //@BeforeEach
  @BeforeAll
  static void setUp() {
    person = new Person();
  }

  @Test
  @Order(2)
  void disablePerson() {
    Long id = person.getId();
    repository.disablePerson(id);
    var result = repository.findById(id);
    person = result.get();
    System.out.println(person);
    assertNotNull(person);
    assertNotNull(person.getId());
    assertNotNull(person.getAddress());
    assertEquals("PO Box 57468", person.getAddress());
    assertEquals("Niko", person.getFirstName());
    assertFalse(person.getEnabled());
  }

  @Test
  @Order(1)
  void findPeopleByName() {
    String direction = "asc";
    int page = 0;
    int size = 12;
    Direction sortDirection = "desc".equalsIgnoreCase(direction) ? Direction.DESC : Direction.ASC;
    Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, "firstName"));
    person = repository.findPeopleByName("iko", pageable).getContent().get(0);
    System.out.println(person);
    assertNotNull(person);
    assertNotNull(person.getId());
    assertNotNull(person.getAddress());
    assertEquals("PO Box 57468", person.getAddress());
    assertEquals("Niko", person.getFirstName());
    assertFalse(person.getEnabled());
  } 
}
