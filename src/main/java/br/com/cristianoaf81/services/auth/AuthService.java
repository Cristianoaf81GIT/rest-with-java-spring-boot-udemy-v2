package br.com.cristianoaf81.services.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.cristianoaf81.dto.security.AccountCredentialsDTO;
import br.com.cristianoaf81.dto.security.TokenDTO;
import br.com.cristianoaf81.repository.UserRepository;
import br.com.cristianoaf81.security.jwt.JwtTokenProvider;

@Service
public class AuthService {

  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private JwtTokenProvider jwtTokenProvider;

  @Autowired
  private UserRepository userRepository;

  public ResponseEntity<TokenDTO> signIn(AccountCredentialsDTO credentials) {
    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(credentials.getUsername(), credentials.getPassword())

    );

    var user = userRepository.findByUserName(credentials.getUsername());

    if (user == null) {
      throw new UsernameNotFoundException("Username [" + credentials.getUsername() + "] not found.");
    }

    var token = jwtTokenProvider.createAccessToken(credentials.getUsername(), user.getRoles());

    return ResponseEntity.ok(token);
  }

}
