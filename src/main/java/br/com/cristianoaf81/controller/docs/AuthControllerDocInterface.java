package br.com.cristianoaf81.controller.docs;

import org.springframework.http.ResponseEntity;

import br.com.cristianoaf81.dto.security.AccountCredentialsDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Authentication Endpoint")
public interface AuthControllerDocInterface {

  @Operation(summary = "Authenticates an user and returns  a token")
  ResponseEntity<?> signIn(AccountCredentialsDTO credentials);

  @Operation(summary = "Returns a token from refreshToken of authenticated user")
  ResponseEntity<?> signInWithRefreshToken(String userName, String refreshToken);

  @Operation(summary = "Creates a new User")
  AccountCredentialsDTO create(AccountCredentialsDTO dto);
}