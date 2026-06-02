package br.com.cristianoaf81.file.exporter.impl;

import java.io.ByteArrayOutputStream;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import br.com.cristianoaf81.dto.v1.PersonDTO;
import br.com.cristianoaf81.file.exporter.contract.FileExporter;

@Component
public class XlsxExporter implements FileExporter {

  @Override
  public Resource ExportFile(List<PersonDTO> people) throws Exception {

    try (Workbook workbook = new XSSFWorkbook()) {
      Sheet sheet = workbook.createSheet("People");

      Row headerRow = sheet.createRow(0);
      String[] headers = { "ID", "First Name", "Last Name", "Address", "Gender", "Enabled" };
      for (int i = 0; i < headers.length; i++) {
        Cell cell = headerRow.createCell(i);
        cell.setCellValue(headers[i]);
        cell.setCellStyle(createHeaderCellStyle(workbook));
      }

      int rowIndex = 1;
      for (PersonDTO person : people) {
        Row row = sheet.createRow(rowIndex++);
        row.createCell(0).setCellValue(person.getId());
        row.createCell(1).setCellValue(person.getFirstName());
        row.createCell(2).setCellValue(person.getLastName());
        row.createCell(3).setCellValue(person.getAddress());
        row.createCell(4).setCellValue(person.getGender());
        String enabledValue = person.getEnabled() != null && person.getEnabled() ? "Yes" : "No";
        row.createCell(5).setCellValue(enabledValue);
      }

      for (int i = 0; i < headers.length; i++) {
        sheet.autoSizeColumn(i);
      }

      ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

      workbook.write(outputStream);

      return new ByteArrayResource(outputStream.toByteArray(), "people.xlsx");
    }

  }

  @Override
  public Resource ExportPerson(PersonDTO person) throws Exception {
    // TODO Auto-generated method stub
    return null;
  }

  private CellStyle createHeaderCellStyle(Workbook workbook) {
    CellStyle style = workbook.createCellStyle();
    Font font = workbook.createFont();
    font.setBold(true);
    style.setFont(font);
    style.setAlignment(HorizontalAlignment.CENTER);
    style.setVerticalAlignment(VerticalAlignment.CENTER);
    return style;
  }
}
