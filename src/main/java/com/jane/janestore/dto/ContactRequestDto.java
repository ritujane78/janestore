package com.jane.janestore.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Setter
public class ContactRequestDto {

    @NotBlank(message = "Name is required")
    @Size(min = 5, max = 30, message = "Name must be between 5 and 30 characters")
    private String name;

    @NotBlank(message = "Email is required")
    @Email(message = "Email must be a valid email address")
    private String email;

    @NotBlank(message = "Mobile number is required")
    @Pattern(regexp = "^(\\d{10})$", message = "Mobile number must be a valid 10-digit number")
    private String mobileNumber;

    @NotBlank(message = "Message is required")
    @Size(min = 5, max = 500, message = "Message must be between 5 and 500 characters")
    private String message;

}
