package com.jane.janestore.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.authentication.password.CompromisedPasswordChecker;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.password.HaveIBeenPwnedRestApiPasswordChecker;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.security.cert.CertPathChecker;
import java.util.Arrays;
import java.util.Collections;

@Configuration
public class WebConfig {

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    return http
      .authorizeHttpRequests(requests -> requests
        .requestMatchers("/api/v1/products/**").permitAll()
          .requestMatchers("/api/v1/auth/**").permitAll()
        .requestMatchers("/api/v1/contacts").permitAll()
        .anyRequest().authenticated()
      )
      .csrf(csrfConfig -> csrfConfig.disable())
      .cors(corsConfig -> corsConfig.configurationSource(corsConfigurationSource()))
      .formLogin(Customizer.withDefaults())
      .httpBasic(Customizer.withDefaults())
      .build();
  }
  public CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration config = new CorsConfiguration();
    config.setAllowCredentials(true);
    config.setAllowedHeaders(Collections.singletonList("*"));
    config.setAllowedMethods(Collections.singletonList("*"));
    config.setAllowedOrigins(Arrays.asList("http://localhost:5173"));
    config.setMaxAge(3600L);


    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", config);
    return source;
  }

  @Bean
  public AuthenticationManager authenticationManagerBean(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) throws Exception {
    var daoAuthenticationProvider = new DaoAuthenticationProvider(userDetailsService);
    daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
    var providerManager = new ProviderManager(daoAuthenticationProvider);
    return providerManager;

  }
  @Bean
  public PasswordEncoder passwordEncoder(){
    return new BCryptPasswordEncoder();
  }

  @Bean
  public CompromisedPasswordChecker compromisedPasswordChecker(){
    return new HaveIBeenPwnedRestApiPasswordChecker();
  }
}
