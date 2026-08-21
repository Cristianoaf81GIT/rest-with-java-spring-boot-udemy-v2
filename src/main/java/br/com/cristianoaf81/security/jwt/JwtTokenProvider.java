package br.com.cristianoaf81.security.jwt;

import java.util.Base64;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import br.com.cristianoaf81.dto.security.TokenDTO;
import br.com.cristianoaf81.exception.InvalidJWTAuthenticationException;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;

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

    String issuerUrl = ServletUriComponentsBuilder.fromCurrentContextPath().build().toString();

    return JWT
        .create()
        .withClaim("roles", roles)
        .withIssuedAt(now)
        .withExpiresAt(validity)
        .withSubject(username)
        .withIssuer(issuerUrl)
        .sign(algorithm);
  }

  private String getRefreshToken(String username, List<String> roles, Date now) {
    Date refreshTokenValidity = new Date(now.getTime() + (validityInMilliseconds * 3));
    return JWT
        .create()
        .withClaim("roles", roles)
        .withIssuedAt(now)
        .withExpiresAt(refreshTokenValidity)
        .withSubject(username)
        .sign(algorithm);
  }

  public Authentication gAuthentication(String token) {
    DecodedJWT decodedJWT = decodedToken(token);
    UserDetails userDetails = this.userDetailsService.loadUserByUsername(decodedJWT.getSubject());
    return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
  }

  private DecodedJWT decodedToken(String token) {
    Algorithm algo = Algorithm.HMAC256(secretKey.getBytes());
    JWTVerifier verifier = JWT.require(algo).build();
    DecodedJWT decodedJWT = verifier.verify(token);
    return decodedJWT;
  }

  public String resolveToken(HttpServletRequest request) {
    String beareToken = request.getHeader("Authorization");
    if (StringUtils.isEmpty(beareToken) && beareToken.startsWith("Bearer ")) {
      return beareToken.substring("Bearer ".length());
    } else {
      throw new InvalidJWTAuthenticationException("Ivalid Jwt Token.");
    }
  }

  public boolean validateToken(String token) {
    DecodedJWT decodedJWT = decodedToken(token);

    try {
      if (decodedJWT.getExpiresAt().before(new Date())) {
        return false;
      }

      return true;
    } catch (Exception e) {
      throw new InvalidJWTAuthenticationException("Expired or invalid Jwt Token.");
    }

  }
}
