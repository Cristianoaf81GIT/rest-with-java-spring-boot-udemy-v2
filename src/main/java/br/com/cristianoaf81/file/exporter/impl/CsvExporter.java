package br.com.cristianoaf81.file.exporter.impl;

import java.io.ByteArrayOutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import br.com.cristianoaf81.dto.v1.PersonDTO;
import br.com.cristianoaf81.file.exporter.contract.FileExporter;

@Component
public class CsvExporter implements FileExporter {

  @Override
  public Resource ExportFile(List<PersonDTO> people) throws Exception {
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    OutputStreamWriter writer = new OutputStreamWriter(outputStream, StandardCharsets.UTF_8);

    CSVFormat csvFormat = CSVFormat.Builder.create()
        .setHeader("Id", "First Name", "Last Name", "Address", "Gender", "Enabled")
        .setSkipHeaderRecord(false)
        .get();

    try (CSVPrinter printer = new CSVPrinter(writer, csvFormat)) {
      for (PersonDTO person : people) {
        printer.printRecord(
            person.getId(),
            person.getFirstName(),
            person.getLastName(),
            person.getAddress(),
            person.getGender(),
            person.getEnabled());

      }
    }
    return new ByteArrayResource(outputStream.toByteArray());
  }

  @Override
  public Resource ExportPerson(PersonDTO person) throws Exception {
    // TODO Auto-generated method stub
    return null;
  }
}
