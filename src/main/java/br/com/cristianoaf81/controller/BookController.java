package br.com.cristianoaf81.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

import br.com.cristianoaf81.controller.docs.BookControllerApiConfig;
import br.com.cristianoaf81.dto.v1.BookDTO;
import br.com.cristianoaf81.services.book.BookService;
import jakarta.validation.Valid;

@RestController
public class BookController implements BookControllerApiConfig {

  @Autowired
  BookService bookService;


 @Override
 public BookDTO create(@Valid @RequestBody BookDTO dto) {
    return bookService.create(dto);
  } 

  @Override
  public ResponseEntity<?> delete(@PathVariable(name = "id") Long id) {
    return bookService.delete(id);
  }

  @Override
  public BookDTO update(@RequestBody BookDTO dto) {
    return bookService.update(dto);
  }

  @Override
  public BookDTO getById(@PathVariable(name = "id") Long id) {
    return bookService.getById(id);
  }

  @Override
  public List<BookDTO> getAll() {
    return bookService.getAll();
  }
}
