package com.clinic.management.services;

import java.util.List;

import com.clinic.management.dtos.DoctorDto;


public interface DoctorService {
	DoctorDto createDoctor(DoctorDto doctorDto,String email);

    DoctorDto getDoctorById(String id);

    List<DoctorDto> getAllDoctors();

    List<DoctorDto> getDoctorsBySpecialization(String specialization);

    List<DoctorDto> getDoctorsByExperience(int years);

    DoctorDto updateDoctor(String doctorId, DoctorDto doctorDto);

    void deleteDoctor(String doctorId);

	DoctorDto getDoctorByUserId(String userId);

}
