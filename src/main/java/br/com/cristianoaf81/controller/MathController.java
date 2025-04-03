package br.com.cristianoaf81.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.cristianoaf81.exception.UnsupportedMathOperationException;

@RestController
@RequestMapping("/math")
public class MathController {
  
  @GetMapping("/sum/{numberOne}/{numberTwo}") // ou @RequestMapping
  public Double sum(
    @PathVariable(name="numberOne") String numberOne,
    @PathVariable("numberTwo") String numberTwo
  ) throws Exception {
    
    if (!isNumeric(numberOne) || !isNumeric(numberTwo)) {
      throw new UnsupportedMathOperationException("Please set a numeric value");
    }    

    return convertToDouble(numberOne) + convertToDouble(numberTwo);
  }

  private boolean isNumeric(String n) {
    try {
      if (n == null || n.isEmpty()) {
        return false;
      }
      String number = n.replace(",",".");
      return number.matches("[-+]?[0-9]*\\.?[0-9]"); 
    } catch (Exception e) {
      return false;
    }
  }

  private Double convertToDouble(String n) throws UnsupportedMathOperationException {
    if (n == null || n.isEmpty()) {
      throw new UnsupportedMathOperationException("Please set a numeric value");
    }

    String number = n.replace(",",".");
    return Double.parseDouble(number);    
  }
}
