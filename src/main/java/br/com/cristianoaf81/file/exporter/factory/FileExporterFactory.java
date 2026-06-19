package br.com.cristianoaf81.file.exporter.factory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import br.com.cristianoaf81.exception.BadRequestException;
import br.com.cristianoaf81.file.exporter.MediaTypes;
import br.com.cristianoaf81.file.exporter.contract.PersonExporter;
import br.com.cristianoaf81.file.exporter.impl.CsvExporter;
import br.com.cristianoaf81.file.exporter.impl.XlsxExporter;
import br.com.cristianoaf81.file.exporter.impl.PdfExporter;

@Component
public class FileExporterFactory {

  private Logger logger = LoggerFactory.getLogger(FileExporterFactory.class);

  @Autowired
  private ApplicationContext context;

  public PersonExporter getExporter(String acceptHeader) {
    String[] parts = acceptHeader.split("\\.");
    String fileExtension = parts[parts.length - 1];

    if (acceptHeader.equalsIgnoreCase(MediaTypes.APPLICATION_XLSX_VALUE)) {
      return context.getBean(XlsxExporter.class);
    } else if (acceptHeader.equalsIgnoreCase(MediaTypes.APPLICATION_CSV_VALUE)) {
      return context.getBean(CsvExporter.class);
    } else if (acceptHeader.equalsIgnoreCase(MediaTypes.APPLICATION_PDF_VALUE)) {
      return context.getBean(PdfExporter.class);
    } else {
      logger.warn("Invalid file format!");
      throw new BadRequestException("Invalid file format: [" + fileExtension + "]");
    }

  }

}
