package br.com.cristianoaf81.file.exporter.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import br.com.cristianoaf81.dto.v1.PersonDTO;
import br.com.cristianoaf81.file.exporter.contract.FileExporter;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Component
public class PdfExporter implements FileExporter {

  @Override
  public Resource ExportFile(List<PersonDTO> people) throws Exception {

    InputStream inputStream = getClass().getResourceAsStream("/templates/people.jrxml");

    if (inputStream == null) {
      throw new RuntimeException("Template file not found: [/templates/people.jrxml]");
    }

    JasperReport jasperReport = JasperCompileManager.compileReport(inputStream);

    JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(people);

    Map<String, Object> parameters = new HashMap<>();
    // example: parameters.put("title", "People report");

    JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

    try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
      JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);
      return new ByteArrayResource(outputStream.toByteArray());
    }
  }

  @Override
  public Resource ExportPerson(PersonDTO person) throws Exception {

    InputStream mainTemplateStream = getClass().getResourceAsStream("/templates/person.jrxml");

    if (mainTemplateStream == null) {
      throw new RuntimeException("Template file not found: [/templates/person.jrxml]");
    }

    InputStream subTemplateStream = getClass().getResourceAsStream("/templates/books.jrxml");

    if (subTemplateStream == null) {
      throw new RuntimeException("Template file not found: [/templates/books.jrxml]");
    }

    JasperReport mainReport = JasperCompileManager.compileReport(mainTemplateStream);
    JasperReport subReport = JasperCompileManager.compileReport(subTemplateStream);

    InputStream qrCodeStream = generateQRCode(person.getProfileUrl(), 200, 200);

    JRBeanCollectionDataSource subDataSource = new JRBeanCollectionDataSource(person.getBooks());

    Map<String, Object> parameters = new HashMap<>();
    parameters.put("SUB_REPORT_DATA_SOURCE", subDataSource);
    parameters.put("BOOK_SUB_REPORT", subReport);
    parameters.put("QR_CODEIMAGE", qrCodeStream);

    JRBeanCollectionDataSource mainDataSource = new JRBeanCollectionDataSource(Collections.singleton(person));

    JasperPrint jasperPrint = JasperFillManager.fillReport(mainReport, parameters, mainDataSource);

    try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
      JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);
      return new ByteArrayResource(outputStream.toByteArray());
    }
  }

  private InputStream generateQRCode(String url, int width, int height) throws Exception {
    QRCodeWriter qrCodeWriter = new QRCodeWriter();
    BitMatrix bitMatrix = qrCodeWriter.encode(url, BarcodeFormat.QR_CODE, width, height);
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    MatrixToImageWriter.writeToStream(bitMatrix, "PNG", outputStream);
    return new ByteArrayInputStream(outputStream.toByteArray());
  }
}
