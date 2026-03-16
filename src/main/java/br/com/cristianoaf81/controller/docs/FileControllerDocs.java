package br.com.cristianoaf81.controller.docs;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.Resource;

import br.com.cristianoaf81.dto.v1.UploadFileResponseDTO;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;

@Tag(name = "File Endpoint")
public interface FileControllerDocs {

  UploadFileResponseDTO uploadFile(MultipartFile file);
  List<UploadFileResponseDTO> uploadMultipleFiles(MultipartFile[] files);
  ResponseEntity<Resource> downloadFile(String filename, HttpServletResponse response);

}
