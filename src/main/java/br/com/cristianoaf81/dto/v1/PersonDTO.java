package br.com.cristianoaf81.dto.v1;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.cristianoaf81.model.Book;

//import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
// @JsonIgnoreProperties({"links"})
@Relation(collectionRelation = "people")
public class PersonDTO extends RepresentationModel<PersonDTO> implements Serializable {

  private static final long serialVersionUID = 7965371053387682674L;

  private Long id;

  private String firstName;

  private String lastName;

  private String address;

  private String gender;

  private Boolean enabled;

  private String profileUrl;

  private String photoUrl;

  @JsonIgnore
  private List<Book> books;

  public PersonDTO() {
  }

  // id
  public void setId(Long id) {
    this.id = id;
  }

  public Long getId() {
    return this.id;
  }

  // firstname
  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getFirstName() {
    return this.firstName;
  }

  // lastname
  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getLastName() {
    return this.lastName;
  }

  // address
  public void setAddress(String address) {
    this.address = address;
  }

  public String getAddress() {
    return this.address;
  }

  // gender
  public void setGender(String gender) {
    this.gender = gender;
  }

  public String getGender() {
    return this.gender;
  }

  // enabled
  public void setEnabled(Boolean enabled) {
    this.enabled = enabled;
  }

  public Boolean getEnabled() {
    return this.enabled;
  }

  // name
  @JsonIgnore
  public String getName() {
    return (firstName != null ? firstName : "")
        + (lastName != null ? " " + lastName : "");
  }

  public String getProfileUrl() {
    return profileUrl;
  }

  public void setProfileUrl(String profileUrl) {
    this.profileUrl = profileUrl;
  }

  public String getPhotoUrl() {
    return photoUrl;
  }

  public void setPhotoUrl(String photoUrl) {
    this.photoUrl = photoUrl;
  }

  public List<Book> getBooks() {
    return books;
  }

  public void setBooks(List<Book> books) {
    this.books = books;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null || getClass() != obj.getClass())
      return false;
    PersonDTO p = (PersonDTO) obj;
    return id == p.getId() &&
        Objects.equals(firstName, p.getFirstName()) &&
        Objects.equals(lastName, p.getLastName()) &&
        Objects.equals(address, p.getAddress()) &&
        Objects.equals(gender, p.getGender()) &&
        Objects.equals(enabled, p.getEnabled()) &&
        Objects.equals(profileUrl, p.getProfileUrl()) &&
        Objects.equals(photoUrl, p.getPhotoUrl()) &&
        Objects.equals(books, p.getBooks());

  }

  @Override
  public int hashCode() {
    return Objects.hash(
        id,
        firstName,
        lastName,
        address,
        gender,
        enabled,
        profileUrl,
        photoUrl,
        books);
  }

  @Override
  public String toString() {
    return "Person [id=" + id
        + ", firstName=" + firstName
        + ", lastName=" + lastName
        + ", address=" + address
        + ", gender=" + gender
        + ", enabled=" + enabled
        + ", profileUrl=" + profileUrl
        + ", photoUrl=" + photoUrl
        + ", books=" + books + "]";
  }

}
