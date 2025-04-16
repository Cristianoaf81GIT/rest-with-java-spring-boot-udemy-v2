package br.com.cristianoaf81.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.cristianoaf81.exception.DivisionByZeroException;
import br.com.cristianoaf81.exception.UnsupportedMathOperationException;
import br.com.cristianoaf81.services.math.SimpleMath;

import static br.com.cristianoaf81.request.converters.NumberConverter.*;

@RestController
@RequestMapping("/math")
public class MathController {

  @Autowired
  private SimpleMath mathService;

  @GetMapping("/sum/{numberOne}/{numberTwo}") // ou @RequestMapping
  public Double sum(
    @PathVariable(name="numberOne") String numberOne,
    @PathVariable("numberTwo") String numberTwo
  ) throws Exception {
    
    if (!isNumeric(numberOne) || !isNumeric(numberTwo)) {
      throw new UnsupportedMathOperationException("Please set a numeric value");
    }    

    return mathService.sum(convertToDouble(numberOne),convertToDouble(numberTwo));
  }

  @GetMapping("/subtraction/{numberOne}/{numberTwo}")
  public Double subtraction(
    @PathVariable(name = "numberOne") String numberOne,
    @PathVariable(name = "numberTwo") String numberTwo
  ) throws Exception {
    
    if (!isNumeric(numberOne) || !isNumeric(numberTwo)) {
      throw new UnsupportedMathOperationException("Please set a numeric value");
    }
    
    return mathService.subtract(convertToDouble(numberOne), convertToDouble(numberTwo));

  }

  @GetMapping("/multiplication/{numberOne}/{numberTwo}")
  public Double multiplication(
    @PathVariable(name = "numberOne") String numberOne,
    @PathVariable(name = "numberTwo") String numberTwo
  ) throws Exception {

    if (!isNumeric(numberOne) || !isNumeric(numberTwo)) {
      throw new UnsupportedMathOperationException("Please set a numeric value");
    }    

    return mathService.multiply(convertToDouble(numberOne), convertToDouble(numberTwo));
  }


  @GetMapping("/division/{numberOne}/{numberTwo}")
  public Double division(
    @PathVariable(name = "numberOne") String numberOne,
    @PathVariable(name = "numberTwo") String numberTwo
  ) throws Exception {

    if (!isNumeric(numberOne) || !isNumeric(numberTwo)) {
      throw new UnsupportedMathOperationException("Please set a numeric value");
    }    

    Double n1 = convertToDouble(numberOne); 
    Double n2 = convertToDouble(numberTwo);

    if (n2 == 0) {
      throw new DivisionByZeroException("Cannot divide by zero");
    }

    return mathService.divide(n1, n2);
  }

  @GetMapping("/mean/{numberOne}/{numberTwo}")
  public Double mean(
    @PathVariable(name = "numberOne") String numberOne,
    @PathVariable(name = "numberTwo") String numberTwo
  ) throws Exception {

    if (!isNumeric(numberOne) || !isNumeric(numberTwo)) {
      throw new UnsupportedMathOperationException("Please set a numeric value");
    }    
    return mathService.mean(convertToDouble(numberOne),convertToDouble(numberTwo));
  }

  @GetMapping("/squareroot/{number}")
  public Double square(
    @PathVariable(name = "number") String number
  ) throws Exception {

    if (!isNumeric(number)) {
      throw new UnsupportedMathOperationException("Please set a numeric value");
    } 

    if (convertToDouble(number) < 0) {
      throw new UnsupportedMathOperationException("Cannot get square root of negative number");
    }

    return mathService.squareRoot(convertToDouble(number));
  }

}
