package com.clinic.management.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.clinic.management.entities.Patient;

import java.util.Optional;


public interface PatientRepository extends JpaRepository<Patient, String>{
	Optional<Patient> findByUserEmail(String email);
	Optional<Patient> findByUserId(String id);

	
	
	

}
