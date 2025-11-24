package br.com.cristianoaf81.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.cristianoaf81.model.Book;

public interface BookRepository extends JpaRepository<Book, Long> {}
