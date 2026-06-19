package br.com.cristianoaf81.model;

import java.io.Serializable;
import java.util.Objects;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "person")
public class Person implements Serializable {

  private static final long serialVersionUID = 3250718952166047026L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "first_name", nullable = false, length = 80)
  private String firstName;

  @Column(name = "last_name", nullable = false, length = 80)
  private String lastName;

  @Column(nullable = false, length = 200)
  private String address;

  @Column(nullable = false, length = 6)
  private String gender;

  @Column(nullable = false)
  private Boolean enabled;

  @Column(name = "wikipedia_profile_url", length = 255)
  private String profileUrl;

  @Column(name = "photo_url", length = 255)
  private String photoUrl;

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(name = "person_books", joinColumns = @JoinColumn(name = "person_id"), inverseJoinColumns = @JoinColumn(name = "book_id"))
  private List<Book> books;

  public Person() {
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
    Person p = (Person) obj;
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
