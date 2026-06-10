package com.clinic.management.dtos;

import java.time.LocalDate;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PatientDto {
	
	private String id; 
	private String firstName;

//	private String lastName;
	

//	private String email;
//	
//
//	private String phoneNo;


	
	private LocalDate dateOfBirth;
	
	private String gender;
	
	private String address;
	
	
	private String medicalHistory;

}
