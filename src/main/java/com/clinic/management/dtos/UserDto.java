package com.clinic.management.dtos;

import com.clinic.management.entities.Doctor;
import com.clinic.management.entities.Role;

import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

	private String id;
	private String username;
	
	
	private String password;
	
	private String fullName;
	
	private String email;
	
	private String phoneNumber;
	
	private Role role;
	private Doctor doctor;

}
