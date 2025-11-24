package br.com.cristianoaf81.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "books")
public class Book implements Serializable {

  private static final long serialVersionUID = -8725245808567158158L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)  
  private Long id;

  @Column(name = "author", nullable = false, length = 200)
  private String author; 

  @Column(name = "launch_date", nullable = false)
  private LocalDate launchDate;

  @Column(name = "price", nullable = false)
  private Double price;


  @Column(name = "title", nullable = false, length = 200)
  private String title;


  //id
  public Long getId() {return this.id;}
  public void setId(Long id) {this.id=id;}

  //author
  public String getAuthor() {return this.author;}
  public void setAuthor(String author) {this.author=author;}

  //launchDate
  public LocalDate getLaunchDate() {return this.launchDate;}
  public void setLaunchDate(LocalDate launchDate) {this.launchDate=launchDate;}

  //price
  public Double getPrice() {return this.price;}
  public void setPrice(Double price) {this.price=price;}

  //title
  public String getTitle() {return this.title;}
  public void setTitle(String title) {this.title=title;}


  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null || getClass() != obj.getClass()) return false;
    Book b = (Book) obj;
    return id == b.getId() && 
      Objects.equals(author, b.getAuthor()) &&
      Objects.equals(launchDate, b.getLaunchDate()) &&
      Objects.equals(price, b.getPrice()) &&
      Objects.equals(title, b.getTitle());
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
