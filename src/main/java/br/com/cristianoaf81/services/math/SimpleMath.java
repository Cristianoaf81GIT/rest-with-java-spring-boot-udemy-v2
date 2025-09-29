package br.com.cristianoaf81.services.math;

import org.springframework.stereotype.Service;

@Service
public class SimpleMath {

  
  public Double sum(Double n1, Double n2) {
    return n1 + n2;
  }

  public Double subtract(Double n1, Double n2) {
    return n1 - n2;
  }

  public Double multiply(Double n1, Double n2) {
    return n1 * n2;
  }

  public Double divide(Double n1, Double n2) {
    return n1 / n2;
  }

  public Double mean(Double n1, Double n2) {
    return (n1 + n2) / 2;
  }

  public Double squareRoot(Double n) {
    return Math.sqrt(n);
  } 

}
