package com.clinic.management.dtos;

import java.time.LocalDateTime;

import com.clinic.management.entities.Doctor;
import com.clinic.management.entities.Patient;
import com.clinic.management.entities.User;
import com.clinic.management.enums.AppointmentStatus;
import com.clinic.management.enums.PaymentStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentDto {
 //many appo. one patient
	
	private String appointmentId;
	private Doctor doctor;
	
	private Patient patient;
	
	private LocalDateTime appoinmentDateTime;
	

	private AppointmentStatus status;
	
	private Double amount;
	
	private String description;
	
	private String razorpayOrderId;
	private String razorpayPaymentId;
	
	private PaymentStatus paymentStatus;

}
