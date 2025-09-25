package br.com.cristianoaf81.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
public class TestLogController {
  
  private Logger logger = LoggerFactory.getLogger(TestLogController.class.getName());

  @GetMapping(value = "/test")
  public String testLog() {
    logger.debug("This is a DEBUG Log!");
    logger.info("This is an INFO Log!");
    logger.warn("This is a WARN Log!");
    logger.error("This is an ERROR Log!");
    logger.trace("This is a TRACE Log!");
    return "Logs generated successfully!";
  }
}
