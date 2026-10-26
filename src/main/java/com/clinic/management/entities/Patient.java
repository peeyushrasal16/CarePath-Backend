package com.clinic.management.entities;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Patient {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private String id;
	
	
//	
//	@Column(nullable = false)
//	private String lastName;
	
//	@Column(unique = true)
//	private String email;
	
//	@Column(nullable = false)
//	private String phoneNo;
	
	private LocalDate dateOfBirth;
	
	private String gender;
	
	private String address;
	
	@Column(length = 500)
	private String medicalHistory;
	
	@OneToOne
	@JoinColumn(name = "patient_id", nullable = false)
	private User user;
	
	

}
