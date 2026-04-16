package br.com.cristianoaf81.file.importer.impl;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.csv.CSVFormat.Builder;
import org.springframework.stereotype.Component;

import br.com.cristianoaf81.dto.v1.PersonDTO;
import br.com.cristianoaf81.file.importer.contract.FileImporter;

@Component
public class CsvImporter implements FileImporter {

  @Override
  public List<PersonDTO> importFile(InputStream is) throws Exception {
    Builder csvBuilder = CSVFormat.Builder
        .create()
        .setHeader()
        .setSkipHeaderRecord(true)
        .setIgnoreEmptyLines(true)
        .setTrim(true);

    CSVFormat format = csvBuilder.get();

    Iterable<CSVRecord> records = format.parse(new InputStreamReader(is));

    return parseRecordsToPersonDTO(records);
  }

  private List<PersonDTO> parseRecordsToPersonDTO(Iterable<CSVRecord> records) {
    List<PersonDTO> people = new ArrayList<>();
    for (CSVRecord rec : records) {
      PersonDTO dto = new PersonDTO();
      dto.setFirstName(rec.get("first_name"));
      dto.setLastName(rec.get("last_name"));
      dto.setAddress(rec.get("address"));
      dto.setGender(rec.get("gender"));
      dto.setEnabled(true);
      people.add(dto);
    }
    return people;
  }
}
