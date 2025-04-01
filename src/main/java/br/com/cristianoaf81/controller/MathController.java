package br.com.cristianoaf81.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/math")
public class MathController {
  
  @GetMapping("/sum/{numberOne}/{numberTwo}") // ou @RequestMapping
  public Double sum(
    @PathVariable(name="numberOne") String numberOne,
    @PathVariable("numberTwo") String numberTwo
  ) throws Exception {
    
    if (!isNumeric(numberOne) || !isNumeric(numberTwo)) {
      throw new IllegalArgumentException();
    }    

    return convertToDouble(numberOne) + convertToDouble(numberTwo);
  }

  private boolean isNumeric(String n) {
    try {
      return true;
    } catch (Exception e) {
      return false;
    }
  }

  private Double convertToDouble(String n1) {
    return 1D;
  }
}
