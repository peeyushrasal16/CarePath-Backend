package com.clinic.management.security;

import com.clinic.management.dtos.UserDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {
private UserDto userDto;
	
	private String token;

}
