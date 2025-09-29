package br.com.cristianoaf81.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.cristianoaf81.model.Person;

public interface PersonRepository extends JpaRepository<Person, Long> {

}
