package br.com.cristianoaf81.services.auth;

import static br.com.cristianoaf81.mapper.ObjectMapper.parseObject;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.cristianoaf81.dto.security.AccountCredentialsDTO;
import br.com.cristianoaf81.dto.security.TokenDTO;
import br.com.cristianoaf81.exception.RequiredObjectIsNullException;
import br.com.cristianoaf81.model.User;
import br.com.cristianoaf81.repository.UserRepository;
import br.com.cristianoaf81.security.jwt.JwtTokenProvider;

import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder.SecretKeyFactoryAlgorithm;

@Service
public class AuthService {

  Logger logger = LoggerFactory.getLogger(AuthService.class);

  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private JwtTokenProvider jwtTokenProvider;

  @Autowired
  private UserRepository userRepository;

  public ResponseEntity<TokenDTO> signIn(AccountCredentialsDTO credentials) {
    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(credentials.getUserName(), credentials.getPassword())

    );

    var user = userRepository.findByUserName(credentials.getUserName());

    if (user == null) {
      throw new UsernameNotFoundException("Username [" + credentials.getUserName() + "] not found.");
    }

    var token = jwtTokenProvider.createAccessToken(credentials.getUserName(), user.getRoles());

    return ResponseEntity.ok(token);
  }

  public ResponseEntity<TokenDTO> refreshToken(String userName, String refreshToken) {
    TokenDTO token = null;
    var user = userRepository.findByUserName(userName);
    if (user == null) {
      throw new UsernameNotFoundException("UserName " + user + " not found!");
    }

    token = jwtTokenProvider.refreshToken(refreshToken);
    return ResponseEntity.ok(token);
  }

  public AccountCredentialsDTO create(AccountCredentialsDTO dto) {
    if (dto == null)
      throw new RequiredObjectIsNullException();

    logger.info("creating a new user!");
    var entity = new User();
    entity.setUserName(dto.getUserName());
    entity.setFullName(dto.getFullName());
    entity.setPassword(generatedHashedPassword(dto.getPassword()));
    entity.setEnabled(true);
    entity.setAccountNonExpired(true);
    entity.setAccountNonLocked(true);
    entity.setCredentialsNonExpired(true);
    return parseObject(userRepository.save(entity), AccountCredentialsDTO.class);
  }

  private static String generatedHashedPassword(String password) {
    Map<String, PasswordEncoder> encoders = new HashMap<>();
    PasswordEncoder pbkdf2Encoder = new Pbkdf2PasswordEncoder(
        "",
        8,
        185000,
        SecretKeyFactoryAlgorithm.PBKDF2WithHmacSHA256);
    encoders.put("pbkdf2", pbkdf2Encoder);
    DelegatingPasswordEncoder passwordEncoder = new DelegatingPasswordEncoder("pbkdf2", encoders);
    passwordEncoder.setDefaultPasswordEncoderForMatches(pbkdf2Encoder);
    return passwordEncoder.encode(password);
  }

}
