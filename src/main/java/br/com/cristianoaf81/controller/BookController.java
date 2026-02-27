package br.com.cristianoaf81.controller;

//import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
//import org.springframework.hateoas.EntityModel;
//import org.springframework.hateoas.PagedModel;


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
  public ResponseEntity<PagedModel<EntityModel<BookDTO>>> getAll(
    @RequestParam(name = "page", defaultValue = "0") Integer page,
    @RequestParam(name = "size", defaultValue = "12") Integer size,
    @RequestParam(name = "direction", defaultValue = "asc") String direction
  ) {
    Direction sortDirection = "desc".equalsIgnoreCase(direction) ? Direction.DESC : Direction.ASC;
    Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, "title"));
    return ResponseEntity.ok(bookService.getAll(pageable));
  }
}
