package br.com.cristianoaf81.controller.docs;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.http.MediaType;

import br.com.cristianoaf81.dto.v1.BookDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RequestMapping("/api/book/v1")
@Tag(name = "Book", description = "Book representation")
public interface BookControllerApiConfig {
  
  @PostMapping(
    produces = {
      MediaType.APPLICATION_JSON_VALUE,
      MediaType.APPLICATION_XML_VALUE,
      MediaType.APPLICATION_YAML_VALUE
    },
    consumes = {
      MediaType.APPLICATION_JSON_VALUE,
      MediaType.APPLICATION_XML_VALUE,
      MediaType.APPLICATION_YAML_VALUE
    }
  )
  @Operation(
    summary = "Creates a new book",
    description = "Create new book",
    tags = {"Book"},
    responses = {      
      @ApiResponse(
        description = "Success",
        responseCode = "200",
        content = {
          @Content(
            mediaType = MediaType.APPLICATION_JSON_VALUE,
            schema = @Schema(implementation = BookDTO.class)
          )
        }
      ),

      @ApiResponse(
        description = "No Content",
        responseCode = "204",
        content = @Content
      ),

      @ApiResponse(
        description = "Bad Request",
        responseCode = "400",
        content = @Content
      ),

      @ApiResponse(
        description = "Unauthorized",
        responseCode = "401",
        content = @Content
      ),

      @ApiResponse(
        description = "Not found",
        responseCode = "404",
        content = @Content
      ),

      @ApiResponse(
        description = "Internal Server Error",
        responseCode = "500",
        content = @Content
      ),
    }    
  )
  public BookDTO create(BookDTO dto);

  @GetMapping(
    value = "/{id}",
    produces = {
      MediaType.APPLICATION_YAML_VALUE,
      MediaType.APPLICATION_XML_VALUE,
      MediaType.APPLICATION_JSON_VALUE
    } 
  )
  @Operation(
    summary = "Return book by its id",
    description = "Get Book by id",
    tags = {"Book"},
    responses = {
      @ApiResponse(
        description = "Success",
        responseCode = "200",
        content = @Content(
          mediaType = MediaType.APPLICATION_JSON_VALUE,
          schema = @Schema(implementation = BookDTO.class)
        )
      ),

      @ApiResponse(
        description = "No Content",
        responseCode = "204",
        content = @Content
      ),

      @ApiResponse(
        description = "Unauthorized",
        responseCode = "401",
        content = @Content
      ),

      @ApiResponse(
        description = "Not found",
        responseCode = "404",
        content = @Content
      ),

      @ApiResponse(
        description = "Internal Server Error",
        responseCode = "500",
        content = @Content
      ),
    }
  )
  public BookDTO getById(Long id);
  
  @GetMapping(
      produces = { 
      MediaType.APPLICATION_JSON_VALUE, 
      MediaType.APPLICATION_XML_VALUE, 
      MediaType.APPLICATION_YAML_VALUE 
    }
  )
  @Operation(
    summary = "Returns books list",
    description = "Get a list of books",
    tags = {"Book"},
    responses = {
      @ApiResponse(
        description = "Success",
        responseCode = "200",
        content = { 
          @Content(
            mediaType = MediaType.APPLICATION_JSON_VALUE, 
            array = @ArraySchema(schema = @Schema(implementation = BookDTO.class))
          ),

          @Content(
            mediaType = MediaType.APPLICATION_XML_VALUE, 
            array = @ArraySchema(schema = @Schema(implementation = BookDTO.class))
          ),
        
          @Content(
            mediaType = MediaType.APPLICATION_YAML_VALUE, 
            array = @ArraySchema(schema = @Schema(implementation = BookDTO.class))
          )
        }
      ),
      
      @ApiResponse(
        description = "No Content",
        responseCode = "204",
        content = @Content
      ),

      @ApiResponse(
        description = "Bad Request",
        responseCode = "400",
        content = @Content
      ),

      @ApiResponse(
        description = "Unauthorized",
        responseCode = "401",
        content = @Content
      ),

      @ApiResponse(
        description = "Not found",
        responseCode = "404",
        content = @Content
      ),

      @ApiResponse(
        description = "Internal Server Error",
        responseCode = "500",
        content = @Content
      ),
    }
  )
  public List<BookDTO> getAll();

  @PutMapping (
    produces = {
      MediaType.APPLICATION_JSON_VALUE, 
      MediaType.APPLICATION_XML_VALUE, 
      MediaType.APPLICATION_YAML_VALUE
    }, 
    consumes = {
      MediaType.APPLICATION_JSON_VALUE, 
      MediaType.APPLICATION_XML_VALUE, 
      MediaType.APPLICATION_YAML_VALUE
    }
  )
  @Operation(
    summary = "Updates a Book Data",
    description = "Update a Book data with JSON/XML/YAML data",
    tags = {"Book"},
    responses = {
      @ApiResponse(
        description = "Success",
        responseCode = "200",
        content = {
          @Content(
            mediaType = MediaType.APPLICATION_JSON_VALUE,
            schema = @Schema(implementation = BookDTO.class)
          )
        }
      ),

      @ApiResponse(
        description = "No Content",
        responseCode = "204",
        content = @Content
      ),

      @ApiResponse(
        description = "Bad Request",
        responseCode = "400",
        content = @Content
      ),

      @ApiResponse(
        description = "Unauthorized",
        responseCode = "401",
        content = @Content
      ),

      @ApiResponse(
        description = "Not found",
        responseCode = "404",
        content = @Content
      ),

      @ApiResponse(
        description = "Internal Server Error",
        responseCode = "500",
        content = @Content
      ),
    }
  )
  public BookDTO update(BookDTO dto);

  @DeleteMapping(value = "/{id}")
  @Operation(
    summary = "Deletes a Book",
    description = "Deletes a specific Book by their ID",
    tags = {"Book"},
    responses = {

      @ApiResponse(
        description = "No Content",
        responseCode = "204",
        content = @Content
      ),

      @ApiResponse(
        description = "Bad Request",
        responseCode = "400",
        content = @Content
      ),

      @ApiResponse(
        description = "Unauthorized",
        responseCode = "401",
        content = @Content
      ),

      @ApiResponse(
        description = "Not found",
        responseCode = "404",
        content = @Content
      ),

      @ApiResponse(
        description = "Internal Server Error",
        responseCode = "500",
        content = @Content
      ),
    }
  )
  public ResponseEntity<?> delete(Long id);
}
