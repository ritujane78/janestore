package com.jane.janestore.controller;

import com.jane.janestore.dto.LoginRequestDto;
import com.jane.janestore.dto.LoginResponseDto;
import com.jane.janestore.dto.RegisterRequestDto;
import com.jane.janestore.dto.UserDto;
import com.jane.janestore.entity.Customer;
import com.jane.janestore.repository.CustomerRepository;
import com.jane.janestore.util.JwtUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.password.CompromisedPasswordChecker;
import org.springframework.security.authentication.password.CompromisedPasswordDecision;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
  private final AuthenticationManager authenticationManager;
  private final JwtUtil jwtUtil;
  private final CustomerRepository customerRepository;
  private final PasswordEncoder passwordEncoder;
  private final CompromisedPasswordChecker compromisedPasswordChecker;

  @PostMapping("/login")
  public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto loginRequestDto){
   try {
     Authentication authentication = authenticationManager.authenticate(
       new UsernamePasswordAuthenticationToken(
         loginRequestDto.username(),
         loginRequestDto.password()));
     var loggedInUser = (User) authentication.getPrincipal();
     UserDto userDto = new UserDto();
     userDto.setName(loggedInUser.getUsername());
     String jwtToken = jwtUtil.generateJwtToken(authentication);
     return ResponseEntity.ok().body
       (new LoginResponseDto(HttpStatus.OK.getReasonPhrase(),userDto,jwtToken));
   }  catch (BadCredentialsException ex) {
     return buildErrorResponse(HttpStatus.UNAUTHORIZED,
       "Invalid username or password");
   } catch (AuthenticationException ex) {
     return buildErrorResponse(HttpStatus.UNAUTHORIZED,
       "Authentication failed");
   } catch (Exception ex) {
     return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR,
       "An unexpected error occurred");
   }

  }
  @PostMapping("/register")
  public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterRequestDto registerRequestDto) {
    Optional<Customer> existingCustomer = customerRepository.findByEmailOrMobileNumber(registerRequestDto.getEmail(), registerRequestDto.getMobileNumber());
    if(existingCustomer.isPresent()) {
      Map<String, String> errors = new HashMap<>();
      Customer customer = existingCustomer.get();
      if (customer.getEmail().equalsIgnoreCase(registerRequestDto.getEmail())) {
        errors.put("email", "Email already exists");
      }
      if ((customer.getMobileNumber().equalsIgnoreCase(registerRequestDto.getMobileNumber()))) {
        errors.put("mobileNumber", "Mobile number already exists");
      }
      return ResponseEntity.badRequest().body(errors);
    }
    CompromisedPasswordDecision decision = compromisedPasswordChecker.check(registerRequestDto.getPassword());
    if (decision.isCompromised()){
      return ResponseEntity.badRequest().body(
        Map.of("password", "Please choose a strong password")
      );
    }

    Customer customer = new Customer();
    BeanUtils.copyProperties(registerRequestDto, customer);
    customer.setPasswordHash(passwordEncoder.encode(registerRequestDto.getPassword()));
    customerRepository.save(customer);
    return ResponseEntity
      .status(HttpStatus.CREATED)
      .body("Registration successful");
  }
  private ResponseEntity<LoginResponseDto> buildErrorResponse(HttpStatus status,
                                                              String message) {
    return ResponseEntity
      .status(status)
      .body(new LoginResponseDto(message, null, null));
  }
}
