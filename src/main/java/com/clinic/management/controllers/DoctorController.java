package com.clinic.management.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.clinic.management.dtos.DoctorDto;
import com.clinic.management.services.DoctorService;

@RestController
@RequestMapping("/doctor")
public class DoctorController {
	@Autowired
	private DoctorService doctorService;
	
	@PostMapping("/register")
    public ResponseEntity<DoctorDto> createDoctor(@RequestBody DoctorDto doctorDto,@RequestParam String email) {
        DoctorDto createdDoctor = doctorService.createDoctor(doctorDto,email);
        return new ResponseEntity<>(createdDoctor, HttpStatus.CREATED);
    }
	
	
	@GetMapping("/{id}")
    public ResponseEntity<DoctorDto> getDoctorById(@PathVariable String id) {
        DoctorDto doctor = doctorService.getDoctorById(id);
        return ResponseEntity.ok(doctor);
    }
	

	@GetMapping
    public ResponseEntity<List<DoctorDto>> getAllDoctors() {
        List<DoctorDto> doctors = doctorService.getAllDoctors();
        return ResponseEntity.ok(doctors);
    }
	
	@GetMapping("/specialization/{specialization}")
    public ResponseEntity<List<DoctorDto>> getDoctorsBySpecialization(@PathVariable String specialization) {
        List<DoctorDto> doctors = doctorService.getDoctorsBySpecialization(specialization);
        return ResponseEntity.ok(doctors);
    }
	
	@GetMapping("/experience/{years}")
    public ResponseEntity<List<DoctorDto>> getDoctorsByExperience(@PathVariable int years) {
        List<DoctorDto> doctors = doctorService.getDoctorsByExperience(years);
        return ResponseEntity.ok(doctors);
    }
	
	 @PutMapping("/{doctorId}")
	    public ResponseEntity<DoctorDto> updateDoctor(
	            @PathVariable String doctorId,
	            @RequestBody DoctorDto doctorDto
	    ) {
	        DoctorDto updatedDoctor = doctorService.updateDoctor(doctorId, doctorDto);
	        return ResponseEntity.ok(updatedDoctor);
	    }
	 
	 @DeleteMapping("/{doctorId}")
	    public ResponseEntity<Void> deleteDoctor(@PathVariable String doctorId) {
	        doctorService.deleteDoctor(doctorId);
	        return ResponseEntity.noContent().build();
	    }


}
