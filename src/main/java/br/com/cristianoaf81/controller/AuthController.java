package br.com.cristianoaf81.controller;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.cristianoaf81.dto.security.AccountCredentialsDTO;
import br.com.cristianoaf81.services.auth.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Authentication Endpoint")
@RestController
@RequestMapping("/auth")
public class AuthController {

  @Autowired
  AuthService service;

  private Logger logger = org.slf4j.LoggerFactory.getLogger(getClass().getName());

  @Operation(summary = "Authenticates an user and returns  a token")
  @PostMapping("/signin")
  public ResponseEntity<?> signIn(@RequestBody() AccountCredentialsDTO credentials) {
    logger.info(credentials.toString());
    logger.info("user " + credentials.getUserName());
    if (credentialsInvalid(credentials)) {
      return ResponseEntity
          .status(HttpStatus.FORBIDDEN)
          .body("Invalid client request no credentials");
    }
    var token = service.signIn(credentials);

    if (token == null) {
      return ResponseEntity
          .status(HttpStatus.FORBIDDEN)
          .body("Invalid client request no token");
    }

    return ResponseEntity.ok(token);
  }

  private static boolean credentialsInvalid(AccountCredentialsDTO credentials) {
    if (credentials == null ||
        StringUtils.isBlank(credentials.getPassword()) ||
        StringUtils.isBlank(credentials.getUserName())) {
      return true;
    }
    return false;
  }
}
