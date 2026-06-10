package com.clinic.management.entities;

import java.time.LocalDateTime;

import com.clinic.management.enums.AppointmentStatus;
import com.clinic.management.enums.PaymentStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Appointment {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private String appointmentId;

	@ManyToOne
	private Doctor doctor; //many appo. one doctor 
	
	@ManyToOne
	private Patient patient; //many appo. one patient
	
	
	private LocalDateTime appoinmentDateTime;
	
	@Enumerated(EnumType.STRING)
	private AppointmentStatus status;
	
	@Column(length = 500)
	private String description;
	
	private Double amount=(double) 700;

	private String razorpayOrderId;
	private String razorpayPaymentId;
	
//	@Enumerated(EnumType.STRING)
//	privatPaymentStatus paymentStatus;
	
	@Enumerated(EnumType.STRING)
	private PaymentStatus paymentStatus;

}
