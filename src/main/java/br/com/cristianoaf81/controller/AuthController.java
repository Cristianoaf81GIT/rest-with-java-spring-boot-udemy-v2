package br.com.cristianoaf81.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
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

  @Operation(summary = "Authenticates an user and returns  a token")
  @PostMapping("/signin")
  public ResponseEntity<?> signIn(AccountCredentialsDTO credentials) {
    if (credentialsInvalid(credentials)) {
      return ResponseEntity
          .status(HttpStatus.FORBIDDEN)
          .body("Invalid cliente request!");
    }
    var token = service.signIn(credentials);

    if (token == null) {
      return ResponseEntity
          .status(HttpStatus.FORBIDDEN)
          .body("Invalid client request!");
    }

    return ResponseEntity.ok(token);
  }

  private static boolean credentialsInvalid(AccountCredentialsDTO credentials) {
    if (credentials == null ||
        StringUtils.isBlank(credentials.getPassword()) ||
        StringUtils.isBlank(credentials.getUsername())) {
      return true;
    }
    return false;
  }
}
