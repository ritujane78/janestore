package com.jane.janestore.dto;

public record LoginResponseDto(String message, UserDto user, String jwtToken) {
}
