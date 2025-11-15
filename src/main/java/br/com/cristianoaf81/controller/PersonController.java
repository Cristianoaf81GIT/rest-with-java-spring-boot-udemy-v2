package br.com.cristianoaf81.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import br.com.cristianoaf81.dto.v1.PersonDTO;
import br.com.cristianoaf81.dto.v2.PersonDTOV2;
import br.com.cristianoaf81.services.person.PersonService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/person/v1")
@Tag(name = "People", description = "Endpoints to manage people")
public class PersonController {

  @Autowired
  private PersonService personService;
   
  // @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
  @GetMapping(
    value = "/{id}", 
    produces = { 
      MediaType.APPLICATION_JSON_VALUE, 
      MediaType.APPLICATION_XML_VALUE, 
      MediaType.APPLICATION_YAML_VALUE 
    } 
  )
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
  public PersonDTO findById(@PathVariable(name = "id") Long id) {
    return personService.findById(id);
  }
  
  // @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
  @GetMapping(
    produces = { 
      MediaType.APPLICATION_JSON_VALUE, 
      MediaType.APPLICATION_XML_VALUE, 
      MediaType.APPLICATION_YAML_VALUE 
    }//,
   /* consumes = {  ****IMPORTANT GET METHOD DOES NOT CONSUMES MEDIA TYPE COMMENT IT*********
      MediaType.APPLICATION_JSON_VALUE, 
      MediaType.APPLICATION_XML_VALUE, 
      MediaType.APPLICATION_YAML_VALUE 
    }*/
  )
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
  public List<PersonDTO> findAll() {
    return personService.findAll();
  }

  // @RequestMapping(method =  RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
  @PostMapping(
    produces = {
      MediaType.APPLICATION_JSON_VALUE, 
      MediaType.APPLICATION_XML_VALUE, 
      MediaType.APPLICATION_YAML_VALUE 
    }, 
    consumes = {
      MediaType.APPLICATION_JSON_VALUE, 
      MediaType.APPLICATION_YAML_VALUE,
      MediaType.APPLICATION_XML_VALUE
    }
  )
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
  public PersonDTO create(@RequestBody PersonDTO person) {
    return personService.create(person);
  }

  @PostMapping(
    value = "/v2",
    produces = {
      MediaType.APPLICATION_JSON_VALUE, 
      MediaType.APPLICATION_XML_VALUE, 
      MediaType.APPLICATION_YAML_VALUE
    }, 
    consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_YAML_VALUE}
  )
  public PersonDTOV2 createV2(@RequestBody PersonDTOV2 person) {
    return personService.createV2(person);
  }

  // @RequestMapping(method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
  @PutMapping(
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
  public PersonDTO update(@RequestBody PersonDTO person) {
    return personService.update(person);
  }

  // @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
  @DeleteMapping(value = "/{id}")
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
  public ResponseEntity<?> delete(@PathVariable(name="id") Long id) {
    return personService.delete(id);
  }
}
