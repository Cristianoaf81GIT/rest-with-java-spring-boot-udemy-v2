package br.com.cristianoaf81.dto.v1;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import org.hibernate.validator.constraints.Length;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

@Relation(collectionRelation = "books")
public class BookDTO extends RepresentationModel<BookDTO> implements Serializable {
  
  private static final long serialVersionUID = -1921762000232113531L;
  
  @Schema(description = "Unique numeric id for Book", example = "1L")
  private Long id;

  @Schema(description = "Book author's name", example = "Fiódor Dostoiévski")
  @NotNull(message = "The author's name is mandatory!")
  @Length(max = 200, message = "Author's name must have maximum of 200 characters!")
  private String author;

  @Schema(description = "Book's lauch date", example = "2002-09-01")
  @NotNull(message = "LaunchDate is mandatory")
  private LocalDate launchDate;

  @Schema(description = "Book's price", example = "43.91")
  @NotNull(message = "Price is mandatory!")
  private Double price;

  @Schema(description = "Book's title", example = "Crime e Castigo")
  @NotNull(message = "Book title is mandatory!")
  @Size(max = 200, message = "Book title must have maximum of 200 characters!")
  private String title;


  public BookDTO() {}

  public BookDTO(Long id, String author, LocalDate launchDate, Double price, String title) {
    this.id = id;
    this.author = author;
    this.launchDate = launchDate;
    this.price = price;
    this.title = title;
  }

  // id
  public Long getId() { return this.id; }
  public void setId(Long id) { this.id = id; }

  // author
  public String getAuthor() { return this.author; }
  public void setAuthor(String author) { this.author = author; }

  // launchDate
  public LocalDate getLaunchDate() { return this.launchDate; }
  public void setLaunchDate(LocalDate launchDate) { this.launchDate = launchDate; }

  // price
  public Double getPrice() { return this.price; }
  public void setPrice(Double price) { this.price = price; }

  // title
  public String getTitle() { return this.title; }
  public void setTitle(String title) { this.title = title;}

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (getClass() != obj.getClass() || obj == null) return false;
    BookDTO dto = (BookDTO) obj;
    return id == dto.getId() 
    && Objects.equals(author, dto.getAuthor())
    && Objects.equals(launchDate, dto.getLaunchDate())
    && Objects.equals(price, dto.getPrice())
    && Objects.equals(title, dto.getTitle());
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, author, launchDate, price, title);
  }

  @Override
  public String toString() {
    String template = "BookDTO [id=%s, author=%s, launchDate=%s, price=%s, title=%s]";
    return String.format(template, id, author, launchDate, price, title);
  }
}
