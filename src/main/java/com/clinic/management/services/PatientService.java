package com.clinic.management.services;

import java.util.List;

import com.clinic.management.dtos.PatientDto;

public interface PatientService {
	PatientDto createPatient(PatientDto patientDto,String email);

    PatientDto getPatientById(String id);

    List<PatientDto> getAllPatients();

    PatientDto updatePatient(String id, PatientDto patientDto);

    void deletePatient(String  id);

}
