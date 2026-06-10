package com.clinic.management.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.clinic.management.entities.Doctor;

public interface DoctorRepository extends JpaRepository<Doctor, String>{
	Optional<Doctor> findByUserId(String id);
	
	

    // Find doctors by specialization
    List<Doctor> findBySpecialization(String specialization);

    // Find doctors with experience greater than or equal to given years
    List<Doctor> findByYearsOfExperienceGreaterThanEqual(int years);

    // Check if license number already exists
    boolean existsByLicenseNumber(String licenseNumber);

}
