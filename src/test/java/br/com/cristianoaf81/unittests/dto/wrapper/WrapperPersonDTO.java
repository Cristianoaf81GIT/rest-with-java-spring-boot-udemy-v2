package br.com.cristianoaf81.unittests.dto.wrapper;

import java.io.Serializable;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

public class WrapperPersonDTO implements Serializable {

  private static final long serialVersionUID = 1l;
  
  @JsonProperty("_embedded")
  private PersonEmbeddedDTO embedded;

  public WrapperPersonDTO() {}


  public PersonEmbeddedDTO getEmbedded() { return this.embedded; }
  public void setEmbedded(PersonEmbeddedDTO embedded) { this.embedded = embedded; }


  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null || getClass() != obj.getClass()) return false;
    WrapperPersonDTO eb = (WrapperPersonDTO) obj;
    return Objects.equals(embedded, eb.getEmbedded());
  }

  @Override
  public int hashCode() {
    return Objects.hash(embedded);
  }

  @Override
  public String toString() {
   return "WrapperPersonDTO [embedded=" + embedded + "]";
  }
}
