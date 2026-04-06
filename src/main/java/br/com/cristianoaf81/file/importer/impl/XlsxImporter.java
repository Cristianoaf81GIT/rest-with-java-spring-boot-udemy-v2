package br.com.cristianoaf81.file.importer.impl;

import java.io.InputStream;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;

import br.com.cristianoaf81.dto.v1.PersonDTO;
import br.com.cristianoaf81.file.importer.contract.FileImporter;

@Component
public class XlsxImporter implements FileImporter {

  @Override
  public List<PersonDTO> importFile(InputStream is) throws Exception {
    
    try (XSSFWorkbook workbook = new XSSFWorkbook(is)) {
      XSSFSheet sheet = workbook.getSheetAt(0);
      Iterator<Row> rowIterator = sheet.iterator();

      if (rowIterator.hasNext()) rowIterator.next(); // pula a primeira linha
      
      return parseRows2PersonDTOList(rowIterator);
    }

  }

  private List<PersonDTO> parseRows2PersonDTOList(Iterator<Row> rowIterator) {
    List<PersonDTO> people = new ArrayList<>();
    while(rowIterator.hasNext()) {
      Row row = rowIterator.next();
      if (isRowValid(row)) {
        people.add(parseRow2PersonDTO(row));
      }
    }
    return people;
  }

  private PersonDTO parseRow2PersonDTO(Row row) {
    PersonDTO dto = new PersonDTO();
    dto.setFirstName(row.getCell(0).getStringCellValue());
    dto.setLastName(row.getCell(1).getStringCellValue());
    dto.setAddress(row.getCell(2).getStringCellValue());
    dto.setGender(row.getCell(3).getStringCellValue());
    dto.setEnabled(true);
    return dto;
  }

  private boolean isRowValid(Row row) {
    return row.getCell(0) != null && row.getCell(0).getCellType() != CellType.BLANK;
  }
}
