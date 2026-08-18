package br.com.cristianoaf81.security.jwt;

import java.util.Base64;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import com.auth0.jwt.algorithms.Algorithm;

import br.com.cristianoaf81.dto.security.TokenDTO;
import jakarta.annotation.PostConstruct;

@Component
public class JwtTokenProvider {

  @Value("${security.jwt.token.secret-key:s3cret}")
  private String secretKey;

  @Value("${security.jwt.token.expire-lenght:3600000}")
  private long validityInMilliseconds = 3600000;

  @Autowired
  private UserDetailsService userDetailsService;

  Algorithm algorithm = null;

  @PostConstruct
  protected void init() {
    secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    algorithm = Algorithm.HMAC256(secretKey.getBytes());
  }

  public TokenDTO createAccessToken(String username, List<String> roles) {
    Date now = new Date();
    Date validity = new Date(now.getTime() + validityInMilliseconds);
    String accessToken = getAccessToken(username, roles, now, validity);
    String refreshToken = getRefreshToken(username, roles, now);
    return new TokenDTO(username, true, now, validity, accessToken, refreshToken);
  }

  private String getAccessToken(String username, List<String> roles, Date now, Date validity) {
    return "";
  }

  private String getRefreshToken(String username, List<String> roles, Date now) {
    return "";
  }

}
