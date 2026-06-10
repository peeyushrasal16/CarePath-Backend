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

import com.clinic.management.dtos.PatientDto;
import com.clinic.management.services.PatientService;

@RestController
@RequestMapping("/patient")
public class PatientController {
	
	@Autowired
	private PatientService patientService;
	
	@PostMapping("register")
	public ResponseEntity<PatientDto> createPatient(@RequestBody PatientDto patientDto,@RequestParam String email)
	{
		PatientDto patient = patientService.createPatient(patientDto,email);
		return new ResponseEntity<>(patient,HttpStatus.CREATED);
	}
	
	@GetMapping("/{email}")
	public ResponseEntity<PatientDto> getPatientById(@PathVariable String email)
	{
		PatientDto patient = patientService.getPatientById(email);
		return ResponseEntity.ok(patient);
	}
	
	@GetMapping
	public ResponseEntity<List<PatientDto>> getAllPatients()
	{
		List<PatientDto> patients = patientService.getAllPatients();
		return ResponseEntity.ok(patients);
	}
	
	@PutMapping("/{id}")
    public ResponseEntity<PatientDto> updatePatient(
            @PathVariable String id,
            @RequestBody PatientDto patientDto) {

        PatientDto updatedPatient = patientService.updatePatient( id, patientDto);
        return ResponseEntity.ok(updatedPatient);
    }

	@DeleteMapping("/{id}")
    public ResponseEntity<String> deletePatient(@PathVariable String id) {
        patientService.deletePatient(id);
        return ResponseEntity.ok("Patient deleted successfully");
    }
}
