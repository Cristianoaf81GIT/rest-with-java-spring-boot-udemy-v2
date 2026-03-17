package br.com.cristianoaf81.controller;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.cristianoaf81.controller.docs.FileControllerDocs;
import br.com.cristianoaf81.dto.v1.UploadFileResponseDTO;
import br.com.cristianoaf81.services.file.FileStorageService;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/file/v1")
public class FileController implements FileControllerDocs {

  private static final Logger logger = LoggerFactory.getLogger(FileController.class); 

  @Autowired
  private FileStorageService service;

	@Override
  @PostMapping("/uploadFile")
	public UploadFileResponseDTO uploadFile(@RequestParam(name = "file") MultipartFile file) {
	  String fileName = service.storeFile(file);
    String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
    .path("/api/file/v1/downloadFile/")
    .path(fileName)
    .toUriString();
    return new UploadFileResponseDTO(fileName, fileDownloadUri, file.getContentType(), file.getSize());
  }

	@Override
  @PostMapping("/uploadMultipleFiles")
	public List<UploadFileResponseDTO> uploadMultipleFiles(@RequestParam(name = "files") MultipartFile[] files) {
    return Arrays.asList(files)
    .stream()
    .map(file -> uploadFile(file))
    .collect(Collectors.toList());
  }

	@Override
  public ResponseEntity<Resource> downloadFile(String filename, HttpServletResponse response) {
	  return null;
  }

}
