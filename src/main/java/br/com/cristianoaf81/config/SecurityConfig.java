package br.com.cristianoaf81.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder.SecretKeyFactoryAlgorithm;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import br.com.cristianoaf81.security.jwt.JwtTokenFilter;
import br.com.cristianoaf81.security.jwt.JwtTokenProvider;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

  @Autowired
  private JwtTokenProvider jwtTokenProvider;

  public SecurityConfig(JwtTokenProvider jwtTokenProvider) {
    this.jwtTokenProvider = jwtTokenProvider;
  }

  @Bean
  PasswordEncoder passwordEncoder() {
    Map<String, PasswordEncoder> encoders = new HashMap<>();
    PasswordEncoder pbkdf2Encoder = new Pbkdf2PasswordEncoder(
        "",
        8,
        185000,
        SecretKeyFactoryAlgorithm.PBKDF2WithHmacSHA256);
    encoders.put("pbkdf2", pbkdf2Encoder);
    DelegatingPasswordEncoder passwordEncoder = new DelegatingPasswordEncoder("pbkdf2", encoders);
    passwordEncoder.setDefaultPasswordEncoderForMatches(pbkdf2Encoder);
    return passwordEncoder;
  }

  @Bean
  AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
    return configuration.getAuthenticationManager();
  }

  SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    JwtTokenFilter filter = new JwtTokenFilter(jwtTokenProvider);
    //@formatter:off
    return http
      .httpBasic(AbstractHttpConfigurer::disable) // desabilita basic auth (user and password via Pbkdf2PasswordEncoder)
      .csrf(AbstractHttpConfigurer::disable) // desabilita o cross site request forgery
      .addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class)
      .sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
      .authorizeHttpRequests((authorizeHttpRequests) -> 
          authorizeHttpRequests.requestMatchers(
            "/auth/signin",
            "/auth/refresh/**",
            "/auth/createUser", // remover após uso em ambiente de desenvolvimento (remover em produção)
            "/swagger-ui/**",
            "/v3/api-docs/**"
          ).permitAll()
          .requestMatchers(
            "/api/**"
          ).authenticated()
          .requestMatchers("/users").denyAll() // bloqueia acesso via jpa a endpoints de usuários
      ).cors((cors) -> {})
      .build();
    //@formatter:on
  }
}
