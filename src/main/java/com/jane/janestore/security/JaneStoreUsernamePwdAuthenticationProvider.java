package com.jane.janestore.security;

import com.jane.janestore.entity.Customer;
import com.jane.janestore.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;
import java.util.Optional;

@Configuration
@RequiredArgsConstructor
public class JaneStoreUsernamePwdAuthenticationProvider implements AuthenticationProvider {
  private final CustomerRepository customerRepository;
  private final PasswordEncoder passwordEncoder;
  @Override
  public @Nullable Authentication authenticate(Authentication authentication) throws AuthenticationException {
    String username = authentication.getName();
    String password = authentication.getCredentials().toString();
    Customer customer = customerRepository.findByEmail(username).orElseThrow(
      () -> new UsernameNotFoundException(
        "User details not found for the user: "+ username
      )
    );
    if(passwordEncoder.matches(password, customer.getPasswordHash())){
      return new UsernamePasswordAuthenticationToken(customer, null, Collections.emptyList());
    }else{
      throw new BadCredentialsException("Invalid password");
    }

  }

  @Override
  public boolean supports(Class<?> authentication) {
    return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
  }
}
