package com.clinic.management.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.clinic.management.dtos.AppointmentDto;
import com.clinic.management.entities.Appointment;

public interface AppointmentRepository extends JpaRepository<Appointment, String>{
	List<Appointment> findByDoctorId(String doctorId);
	
	List<Appointment> findByPatientId(String patientId);
	
	Optional<Appointment> findByRazorpayOrderId(String razorpayOrderId);
	
	

}
