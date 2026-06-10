package com.clinic.management.services;

import java.util.List;

import com.clinic.management.dtos.AppointmentDto;

public interface AppointmentService {
	AppointmentDto createAppointmentsByDoctor(String doctorId,AppointmentDto appointmentDto);
	AppointmentDto bookAppointment(String appointmentId,String patientId);
    List<AppointmentDto> getAppointmentsForDoctor(String doctorId);
    List<AppointmentDto> getAppointmentsForPatient(String patientId);
    AppointmentDto updateAppointmentStatus(String appointmentId, String status);
    AppointmentDto cancelAppointment(String appointmentId);
    
    boolean confirmPayment(String orderId, String paymentId); //void: we can also give Map of string string and payload and boolean return type

}
