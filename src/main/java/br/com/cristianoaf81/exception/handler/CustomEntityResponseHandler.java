package br.com.cristianoaf81.exception.handler;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import br.com.cristianoaf81.exception.CustomExceptionResponse;
import br.com.cristianoaf81.exception.RequiredObjectIsNullException;
import br.com.cristianoaf81.exception.ResourceNotFoundException;
import br.com.cristianoaf81.exception.UnsupportedMathOperationException;
import br.com.cristianoaf81.exception.DivisionByZeroException;

@RestController
@ControllerAdvice
public class CustomEntityResponseHandler extends ResponseEntityExceptionHandler {
  
  @ExceptionHandler(Exception.class)
  public final ResponseEntity<CustomExceptionResponse> handleAllExceptions(Exception ex, WebRequest request) {
    LocalDateTime timestamp = LocalDateTime.now();
    String message = ex.getMessage();
    String details = request.getDescription(false);
    CustomExceptionResponse response = new CustomExceptionResponse(timestamp, message, details);
    return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(UnsupportedMathOperationException.class)
  public final ResponseEntity<CustomExceptionResponse> handleBadRequestExceptions(Exception ex, WebRequest request) {
    LocalDateTime timestamp = LocalDateTime.now();
    String message = ex.getMessage();
    String details = request.getDescription(false);
    CustomExceptionResponse response = new CustomExceptionResponse(timestamp, message, details);
    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(DivisionByZeroException.class)
  public final ResponseEntity<CustomExceptionResponse> handleDivisionByZeroException(Exception ex, WebRequest request) {
    LocalDateTime timestamp = LocalDateTime.now();
    String message = ex.getMessage();
    String details = request.getDescription(false);
    CustomExceptionResponse response = new CustomExceptionResponse(timestamp, message, details);
    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(ResourceNotFoundException.class)
  public final ResponseEntity<CustomExceptionResponse> handleNotFoundExceptions(Exception ex, WebRequest request) {
    LocalDateTime timestamp = LocalDateTime.now();
    String message = ex.getMessage();
    String details = request.getDescription(false);
    CustomExceptionResponse response = new CustomExceptionResponse(timestamp, message, details);
    return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(RequiredObjectIsNullException.class)
  public final ResponseEntity<CustomExceptionResponse> handleRequiredObjectIsNullException(Exception ex, WebRequest request) {
    LocalDateTime timestamp = LocalDateTime.now();
    String message = ex.getMessage();
    String details = request.getDescription(false);
    CustomExceptionResponse response = new CustomExceptionResponse(timestamp, message, details);
    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
  }

}
