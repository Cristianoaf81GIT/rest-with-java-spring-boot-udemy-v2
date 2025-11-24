package br.com.cristianoaf81.services.book;

import java.util.function.Supplier;
import java.util.logging.Logger;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.cristianoaf81.controller.BookController;
import br.com.cristianoaf81.dto.v1.BookDTO;
import br.com.cristianoaf81.exception.RequiredObjectIsNullException;
import br.com.cristianoaf81.exception.ResourceNotFoundException;
import br.com.cristianoaf81.repository.BookRepository;
import br.com.cristianoaf81.model.Book;

import static br.com.cristianoaf81.mapper.ObjectMapper.parseObject;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static br.com.cristianoaf81.mapper.ObjectMapper.parseListObjects;

@Service
public class BookService {

  @Autowired
  private BookRepository bookRepository;

  private Logger logger = Logger.getLogger(BookService.class.getName());


  public BookDTO create(BookDTO dto) {
    if (dto == null) throw new RequiredObjectIsNullException();
    logger.info("Creating a new book");
    Book book = parseObject(dto, Book.class);
    BookDTO bookDTO = parseObject(bookRepository.save(book), BookDTO.class);
    addHateosLinks(dto);
    return bookDTO;
  }

  public ResponseEntity<?> delete(Long id) {
    String msgTemplate = "No record found for book with id: [%s]";
    Book book = bookRepository.findById(id)
      .orElseThrow(() -> new ResourceNotFoundException(String.format(msgTemplate, id)));
    if (book != null) {
      logger.info(String.format("Deleting book with id: [%s]", id));
      bookRepository.delete(book);
    }

    return ResponseEntity.noContent().build();
  }

  public BookDTO update(BookDTO dto) {
    if (dto == null || dto.getId() == null) throw new RequiredObjectIsNullException();
    String template = "No record found for book with id: [%s]";
    String msgError = String.format(template, dto.getId());
    Supplier<ResourceNotFoundException> supplier = () -> new ResourceNotFoundException(msgError);
    Book book = bookRepository.findById(dto.getId()).orElseThrow(supplier);
    logger.info("book.getId() " + book.getId()); 
    if (dto.getId() != null && dto.getId() > 0) {
      book.setId(dto.getId());
    }
    if (dto.getAuthor() != null && !dto.getAuthor().isEmpty()) {
      book.setAuthor(dto.getAuthor());
    }
    if (dto.getPrice() != null && dto.getPrice() > -1L) { 
      book.setPrice(dto.getPrice());
    }
    if (dto.getLaunchDate() != null && !dto.getLaunchDate().toString().isEmpty()) {
      book.setLaunchDate(dto.getLaunchDate());  
    }
    if (dto.getTitle() != null && !dto.getTitle().isEmpty()) {
      book.setTitle(dto.getTitle());
    }

    logger.info("updating book");
    BookDTO updatedBookDTO = parseObject(bookRepository.save(book), BookDTO.class);
    addHateosLinks(dto);
    return updatedBookDTO;    
  }

  public BookDTO getById(Long id) {
    if (id == null) throw new RequiredObjectIsNullException("The id is required");
    String template = "No record found for book with id: [%s]";
    String errorMsg = String.format(template, id);
    Supplier<ResourceNotFoundException> supplier = () -> new ResourceNotFoundException(errorMsg);
    Book book = bookRepository.findById(id).orElseThrow(supplier);
    BookDTO dto = parseObject(book, BookDTO.class);
    addHateosLinks(dto);
    return dto;
  }

  public List<BookDTO> getAll() {
    List<BookDTO> dtos = parseListObjects(bookRepository.findAll(), BookDTO.class);
    dtos.forEach(this::addHateosLinks);
    return dtos;
  }

  public void addHateosLinks(BookDTO dto) {
    dto.add(
      linkTo(
        methodOn(BookController.class).getById(dto.getId())
      ).withSelfRel().withType("GET"));

    dto.add(
      linkTo(
        methodOn(BookController.class).getAll()
      ).withRel("getAll").withType("GET"));

    dto.add(
      linkTo(
        methodOn(BookController.class).delete(dto.getId())
      ).withRel("delete").withType("DELETE"));

  
    dto.add(
      linkTo(
        methodOn(BookController.class).create(dto)
      ).withRel("create").withType("POST"));

    dto.add(
      linkTo(
        methodOn(BookController.class).update(dto)
      ).withRel("update").withType("PUT"));
  }

}
