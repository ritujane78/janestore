package com.jane.janestore.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

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
  public UserDetailsService userDetailsService(){
    var user1 = User.builder().username("ritu").password("$2a$12$i42LOm2wLOjH7pxDhusAHebOryChS2k69hixxJcvd8z0AeSsKRdnK")
      .build();
    var user2 = User.builder().username("jane").password("$2a$12$i42LOm2wLOjH7pxDhusAHebOryChS2k69hixxJcvd8z0AeSsKRdnK")
      .build();

    return new InMemoryUserDetailsManager(Arrays.asList(user1,user2));
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
  public CommandLineRunner checkUsers(UserDetailsService userDetailsService) {
    return args -> {
      for (String username : Arrays.asList("ritu", "jane")) {
        try {
          var user = userDetailsService.loadUserByUsername(username);

          System.out.println("User created: " + user.getUsername());
          System.out.println("Enabled: " + user.isEnabled());
          System.out.println("Authorities: " + user.getAuthorities());
        } catch (Exception e) {
          System.out.println("User not found: " + username);
        }
      }
    };
  }
}
