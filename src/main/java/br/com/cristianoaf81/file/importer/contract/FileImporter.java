package br.com.cristianoaf81.file.importer.contract;

import java.io.InputStream;
import java.util.List;

import br.com.cristianoaf81.dto.v1.PersonDTO;

public interface FileImporter {

  List<PersonDTO> importFile(InputStream is) throws Exception;

}
