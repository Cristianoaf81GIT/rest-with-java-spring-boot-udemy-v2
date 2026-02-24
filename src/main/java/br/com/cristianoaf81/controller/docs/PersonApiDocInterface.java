package br.com.cristianoaf81.controller.docs;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import br.com.cristianoaf81.dto.v1.PersonDTO;

@Tag(name = "People", description = "Endpoints to manage people")
public interface PersonApiDocInterface {
    @Operation(
    summary = "Finds a person",
    description = "Find a specific person by your id",
    tags = {"People"},
    responses = {
      @ApiResponse(
        description = "Success",
        responseCode = "200",
        content = @Content(
          mediaType = MediaType.APPLICATION_JSON_VALUE,
          schema = @Schema(implementation = PersonDTO.class)
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

  public PersonDTO findById(Long id);


  @Operation(
    summary = "Find All People",
    description = "Get A list of people",
    tags = {"People"},
    responses = {
      @ApiResponse(
        description = "Success",
        responseCode = "200",
        content = {
          @Content(
            mediaType = MediaType.APPLICATION_JSON_VALUE,
            array = @ArraySchema(schema = @Schema(implementation = PersonDTO.class))
          ),
          @Content(
            mediaType = MediaType.APPLICATION_XML_VALUE,
            array = @ArraySchema(schema = @Schema(implementation = PersonDTO.class))
          ),
          @Content(
            mediaType = MediaType.APPLICATION_YAML_VALUE,
            array = @ArraySchema(schema = @Schema(implementation = PersonDTO.class))
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
  public ResponseEntity<PagedModel<EntityModel<PersonDTO>>> findAll(
    @RequestParam(name = "page", defaultValue = "0") Integer page,
    @RequestParam(name = "size", defaultValue = "12") Integer size,
    @RequestParam(name = "direction", defaultValue = "asc") String direction
  );

    @Operation(
    summary = "Adds a new Person",
    description = "Adds a new Person (create) passing JSON/YAML/XML representation of PersonDTO.class",
    tags = {"People"},
    responses = {

      @ApiResponse(
        description = "Success",
        responseCode = "200",
        content = {
          @Content(
            mediaType = MediaType.APPLICATION_JSON_VALUE,
            schema = @Schema(implementation = PersonDTO.class)
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
  public PersonDTO create(PersonDTO person);

    @Operation(
    summary = "Updates a Person Data",
    description = "Update a person data with JSON/XML/YAML data",
    tags = {"People"},
    responses = {
      @ApiResponse(
        description = "Success",
        responseCode = "200",
        content = {
          @Content(
            mediaType = MediaType.APPLICATION_JSON_VALUE,
            schema = @Schema(implementation = PersonDTO.class)
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
  public PersonDTO update(PersonDTO person);


  @Operation(
    summary = "Deletes a Person",
    description = "Deletes a specific person by their ID",
    tags = {"People"},
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

  @Operation(
    summary = "Disable a person",
    description = "Disable a specific person by your id",
    tags = {"People"},
    responses = {
      @ApiResponse(
        description = "Success",
        responseCode = "200",
        content = @Content(
          mediaType = MediaType.APPLICATION_JSON_VALUE,
          schema = @Schema(implementation = PersonDTO.class)
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

  public PersonDTO disablePerson(Long id);

}

