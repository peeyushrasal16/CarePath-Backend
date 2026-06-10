package com.clinic.management.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.clinic.management.dtos.UserDto;
import com.clinic.management.repositories.UserRepository;
import com.clinic.management.services.UserService;

@RestController
@RequestMapping("/users")
public class UserController {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserService userService;
	
	@PostMapping("/register")
	public ResponseEntity<UserDto> registerUser(@RequestBody UserDto userDto)
	{
		UserDto savedUser=userService.registerUser(userDto);
		return new ResponseEntity<UserDto>(savedUser,HttpStatus.CREATED);
	}
	
	@GetMapping("/exists")
	public ResponseEntity<Boolean> existsByEmail(@RequestParam String email)
	{
		return ResponseEntity.ok(userRepository.existsByEmail(email));
	}
}
