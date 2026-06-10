package com.clinic.management.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.clinic.management.dtos.AppointmentDto;
import com.clinic.management.services.AppointmentService;
import com.clinic.management.services.RazorPayService;
import com.razorpay.RazorpayException;

@RestController
@RequestMapping("/appointments")
public class AppointmentController {
	
	@Autowired
	private AppointmentService appointmentService;
	
	@Autowired 	
	private RazorPayService razorpayService;
	
	@PostMapping("/{doctorId}/create")
	public ResponseEntity<AppointmentDto> createAppointment(
	        @PathVariable String doctorId,
	        @RequestBody AppointmentDto appointmentDto) {

	    AppointmentDto created =
	            appointmentService.createAppointmentsByDoctor(doctorId, appointmentDto);

	    return ResponseEntity.ok(created);
	}
	
	@PutMapping("/book/{patientId}/{appointmentId}")
	public ResponseEntity<AppointmentDto> bookAppointment(@PathVariable String appointmentId, @PathVariable String patientId)
	{
		AppointmentDto savedAppointment = appointmentService.bookAppointment(appointmentId,patientId);
		return ResponseEntity.ok(savedAppointment);
	}
	
	
	@GetMapping("/{doctorId}")
	public ResponseEntity<List<AppointmentDto>> getAppointmentsForDoctor(@PathVariable String doctorId)
	{
		List<AppointmentDto> appointmentsForDoctor = appointmentService.getAppointmentsForDoctor(doctorId);
		return ResponseEntity.ok(appointmentsForDoctor);
	}
	
	@GetMapping("/patient/{patientId}")
	public ResponseEntity<List<AppointmentDto>> getAppointmentsForPatient(@PathVariable String patientId)
	{
		List<AppointmentDto> appointmentsForPatient = appointmentService.getAppointmentsForPatient(patientId);
		return ResponseEntity.ok(appointmentsForPatient);
	}
	
	
	@PutMapping("/{appointmentId}/status")
	public ResponseEntity<AppointmentDto> updateAppointmentStatus(
			@PathVariable String appointmentId,@RequestParam String status)
	{
		AppointmentDto updateAppointmentStatus = appointmentService.updateAppointmentStatus(appointmentId, status);
		return ResponseEntity.ok(updateAppointmentStatus);
	}
	
	@PutMapping("/{appointmentId}/cancel")
	public ResponseEntity<AppointmentDto> cancelAppointment(@PathVariable String appointmentId) {
	    // Call service to cancel the appointment
	    AppointmentDto cancelledAppointment = appointmentService.cancelAppointment(appointmentId);
	    return ResponseEntity.ok(cancelledAppointment);
	}
	
//	@PostMapping("/confirm")
//	public ResponseEntity<String> confirmEnrollment(@RequestBody Map<String, String> payload)
//	        throws RazorpayException {
//
//	    System.out.println("===== PAYMENT CONFIRM API HIT =====");
//	    System.out.println("Payload received: " + payload);
//
//	    String orderId = payload.get("razorpayOrderId");
//	    String paymentId = payload.get("razorpayPaymentId");
//	    String signature = payload.get("razorpaySignature");
//
//	    System.out.println("OrderId: " + orderId);
//	    System.out.println("PaymentId: " + paymentId);
//	    System.out.println("Signature: " + signature);
//
//	    boolean verified = razorpayService.verifyPayment(paymentId,orderId,  signature);
//
//	    System.out.println("Verified: " + verified);
//
//	    if (!verified) {
//	        return ResponseEntity.badRequest().body("Payment verification failed");
//	    }
//
//	    appointmentService.confirmPayment(orderId, paymentId);
//
//	    return ResponseEntity.ok("Enrollment successful");
//	}
	@PostMapping("/confirm")
	public ResponseEntity<String> confirmPayment(@RequestBody Map<String, String> payload)
	        throws RazorpayException {

	    String orderId = payload.get("razorpayOrderId");
	    String paymentId = payload.get("razorpayPaymentId");
	    String signature = payload.get("razorpaySignature");

	    boolean verified = razorpayService.verifyPayment(paymentId, orderId, signature);

	    if (!verified) {
	        return ResponseEntity.badRequest().body("Payment verification failed");
	    }

	    appointmentService.confirmPayment(orderId, paymentId);

	    return ResponseEntity.ok("Payment successful");
	}

}


