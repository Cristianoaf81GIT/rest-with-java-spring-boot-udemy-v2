package br.com.cristianoaf81.exception.handler;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import br.com.cristianoaf81.exception.ExceptionResponse;
import br.com.cristianoaf81.exception.UnsupportedMathOperationException;
import br.com.cristianoaf81.exception.DivisionByZeroException;

@RestController
@ControllerAdvice
public class CustomEntityResponseHandler extends ResponseEntityExceptionHandler {
  
  @ExceptionHandler(Exception.class)
  public final ResponseEntity<ExceptionResponse> handleAllExceptions(Exception ex, WebRequest request) {
    Date timestamp = new Date();
    String message = ex.getMessage();
    String details = request.getDescription(false);
    ExceptionResponse response = new ExceptionResponse(timestamp, message, details);
    return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(UnsupportedMathOperationException.class)
  public final ResponseEntity<ExceptionResponse> handleBadRequestExceptions(Exception ex, WebRequest request) {
    Date timestamp = new Date();
    String message = ex.getMessage();
    String details = request.getDescription(false);
    ExceptionResponse response = new ExceptionResponse(timestamp, message, details);
    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(DivisionByZeroException.class)
  public final ResponseEntity<ExceptionResponse> handleDivisionByZeroException(Exception ex, WebRequest request) {
    Date timestamp = new Date();
    String message = ex.getMessage();
    String details = request.getDescription(false);
    ExceptionResponse response = new ExceptionResponse(timestamp, message, details);
    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
  }

}
