package br.com.cristianoaf81.services.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import org.springframework.stereotype.Service;

import br.com.cristianoaf81.repository.UserRepository;

@Service
public class UserService implements UserDetailsService {

  // injeção via propriedade é mais flexível, mas com mais chaces de null pointer
  @Autowired
  UserRepository repository;

  // injeção via constructor causa mais acoplamento, mas a aplicação é forçada a
  // iniciar com as dependencias já instanciadas
  public UserService(UserRepository repository) {
    this.repository = repository;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    var user = repository.findByUserName(username);
    if (user != null) {
      return user;
    } else {
      throw new UsernameNotFoundException("Username {" + username + "} not found:");
    }
  }

}
