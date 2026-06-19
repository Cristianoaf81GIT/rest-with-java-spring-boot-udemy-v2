package br.com.cristianoaf81.file.exporter.contract;

import java.util.List;

import org.springframework.core.io.Resource;

import br.com.cristianoaf81.dto.v1.PersonDTO;

public interface PersonExporter {

  Resource ExportPeople(List<PersonDTO> people) throws Exception;

  Resource ExportPerson(PersonDTO person) throws Exception;

}
