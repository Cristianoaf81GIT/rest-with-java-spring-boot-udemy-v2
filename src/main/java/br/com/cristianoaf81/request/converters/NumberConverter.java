package br.com.cristianoaf81.request.converters;

import br.com.cristianoaf81.exception.UnsupportedMathOperationException;

public class NumberConverter {

  public static boolean isNumeric(String n) {
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

  public static Double convertToDouble(String n) throws UnsupportedMathOperationException {
    if (n == null || n.isEmpty()) {
      throw new UnsupportedMathOperationException("Please set a numeric value");
    }

    String number = n.replace(",",".");
    return Double.parseDouble(number);    
  }

}
