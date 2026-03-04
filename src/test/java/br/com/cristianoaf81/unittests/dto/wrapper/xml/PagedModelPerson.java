package br.com.cristianoaf81.unittests.dto.wrapper.xml;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import br.com.cristianoaf81.unittests.dto.PersonDTO;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class PagedModelPerson implements Serializable {

  private static final long serialVersionUID = 1l;

  @XmlElement(name = "content")
  public List<PersonDTO> content;

  public PagedModelPerson() {}

  public List<PersonDTO> getContent() {
	return content;
  }

  public void setContent(List<PersonDTO> content) {
	this.content = content;
  } 

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null || getClass() != obj.getClass()) 
      return false;
    PagedModelPerson p = (PagedModelPerson) obj;
    return Objects.equals(content, p.getContent());
  }

  @Override
  public int hashCode() {
    return Objects.hash(content);
  }

  @Override
  public String toString() {
    return String.format("PagedModel [content=%s]", content);
  }
}
