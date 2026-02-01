package br.com.cristianoaf81.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.cristianoaf81.model.Person;

public interface PersonRepository extends JpaRepository<Person, Long> {
  
  // limpa o cache de nivel 2 do hibernate permitindo sempre resultados atualizados
  @Modifying(clearAutomatically = true)  
  @Query("UPDATE Person p set p.enabled = false WHERE p.id = :id")
  void disablePerson(@Param("id") Long id);

}
