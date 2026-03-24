package br.com.cristianoaf81.file.importer.factory;

import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import br.com.cristianoaf81.exception.BadRequestException;
import br.com.cristianoaf81.file.importer.contract.FileImporter;
import br.com.cristianoaf81.file.importer.impl.CsvImporter;
import br.com.cristianoaf81.file.importer.impl.XlsxImporter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class FileImporterFactory {

  private Logger logger = LoggerFactory.getLogger(FileImporterFactory.class);

  @Autowired
  private ApplicationContext context;
  
  public FileImporter getImporter(String fileName) {
    String[] parts = fileName.split("\\.");
    String fileExtension = parts[parts.length -1];

    if (fileName.endsWith(".xlsx")) {
      return context.getBean(XlsxImporter.class);
      //return new XlsxImporter();
    } else if(fileName.endsWith(".csv")) {
      return context.getBean(CsvImporter.class);
      //return new CsvImporter();
    } else {
      logger.warn("Invalid file format!");
      throw new BadRequestException("Invalid file format: [" + fileExtension + "]"); 
    }

  }

}
