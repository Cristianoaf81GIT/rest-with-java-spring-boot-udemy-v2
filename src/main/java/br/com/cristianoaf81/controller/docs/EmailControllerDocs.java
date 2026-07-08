package br.com.cristianoaf81.controller.docs;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import br.com.cristianoaf81.dto.request.EmailRequestDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "e-Mail", description = "Endpoints send email")
public interface EmailControllerDocs {

  @Operation(summary = "Send an e-Mail", description = "Sends an e-Mail providing details, subject, an body", tags = {
      "e-Mail" }, responses = {
          @ApiResponse(description = "Success", responseCode = "200", content = @Content),

          @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),

          @ApiResponse(description = "Not found", responseCode = "404", content = @Content),

          @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content),
      })
  ResponseEntity<String> sendEmail(EmailRequestDTO emailRequestDTO);

  @Operation(summary = "Send an e-Mail with attachment", description = "Sends an e-Mail with attavhment providing details, subject, an body", tags = {
      "e-Mail" }, responses = {
          @ApiResponse(description = "Success", responseCode = "200", content = @Content),

          @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),

          @ApiResponse(description = "Not found", responseCode = "404", content = @Content),

          @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content),
      })
  ResponseEntity<String> sendEmailWithAttachment(String emailRequestJson, MultipartFile multipartFile);
}
