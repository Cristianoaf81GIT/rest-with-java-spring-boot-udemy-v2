package br.com.cristianoaf81.file.exporter.impl;

import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import br.com.cristianoaf81.dto.v1.PersonDTO;
import br.com.cristianoaf81.file.exporter.contract.FileExporter;

@Component
public class XlsxExporter implements FileExporter {

  @Override
  public Resource ExportFile(List<PersonDTO> people) throws Exception {
    // TODO Auto-generated method stub
    return null;
  }
}
