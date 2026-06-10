package com.clinic.management.services.impls;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.clinic.management.dtos.AppointmentDto;
import com.clinic.management.entities.Appointment;
import com.clinic.management.entities.Doctor;
import com.clinic.management.entities.Patient;
import com.clinic.management.entities.User;
import com.clinic.management.enums.AppointmentStatus;
import com.clinic.management.enums.PaymentStatus;
import com.clinic.management.repositories.AppointmentRepository;
import com.clinic.management.repositories.DoctorRepository;
import com.clinic.management.repositories.PatientRepository;
import com.clinic.management.repositories.UserRepository;
import com.clinic.management.services.AppointmentService;
import com.clinic.management.services.RazorPayService;

import com.razorpay.Order;
import com.razorpay.RazorpayException;

@Service
public class AppointmentServiceImpl implements AppointmentService{
	
	@Autowired
	private AppointmentRepository appointmentRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private DoctorRepository doctorRepository;
	
	@Autowired
	private PatientRepository patientRepository;
	
	@Autowired
	private RazorPayService razorpayService;

	@Override
	public AppointmentDto bookAppointment(String appointmentId,String patientId) {
		
		System.out.println("id "+appointmentId+"Bye Bye");
	     
		Appointment appointment=appointmentRepository.findById(appointmentId)
				.orElseThrow(()-> new RuntimeException("Appointment Not Found"));
		
		Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new RuntimeException("Patient not found"));
		
        
        appointment.setPatient(patient);
       
        appointment.setStatus(AppointmentStatus.SCHEDULED);

        
        //added later
        Order order = null;
		try {
			order = razorpayService.createOrder(appointment.getAmount());//doctor amount
		} catch (RazorpayException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		appointment.setRazorpayOrderId(order.get("id"));
		appointment.setPaymentStatus(PaymentStatus.CREATED);
		
        Appointment saved = appointmentRepository.save(appointment);

        return modelMapper.map(saved, AppointmentDto.class);
		
//		return null;
	}

	@Override
	public List<AppointmentDto> getAppointmentsForDoctor(String doctorId) {
		
		return appointmentRepository.findByDoctorId(doctorId)
				.stream().map(a-> modelMapper.map(a,AppointmentDto.class))
				.toList();
	}

	@Override
	public List<AppointmentDto> getAppointmentsForPatient(String patientId) {
		return appointmentRepository.findByPatientId(patientId)
				.stream().map(a-> modelMapper.map(a,AppointmentDto.class))
				.toList();
	}

	@Override
	public AppointmentDto updateAppointmentStatus(String appointmentId, String status) {
	 Appointment appointment=appointmentRepository.findById(appointmentId)
			 .orElseThrow(()->new RuntimeException("Appointment Not Found"));
	 
	
	 
	 try {
	        AppointmentStatus newStatus = AppointmentStatus.valueOf(status.toUpperCase());
	        appointment.setStatus(newStatus);
	    } catch (IllegalArgumentException e) {
	        throw new RuntimeException("Invalid Appointment Status: " + status);
	    }
	 
	Appointment updatedAppointment=appointmentRepository.save(appointment);
		return modelMapper.map(updatedAppointment, AppointmentDto.class);
	}

	@Override
	public AppointmentDto createAppointmentsByDoctor(String doctorId,AppointmentDto appointmentDto) {
		// TODO Auto-generated method stub
		   Doctor doctor = doctorRepository.findById(doctorId)
		            .orElseThrow(() -> new RuntimeException("Doctor not found"));
		  
		   Appointment appointment = new Appointment();
		    appointment.setDoctor(doctor);
		    
		    appointment.setAppoinmentDateTime(
		            appointmentDto.getAppoinmentDateTime());
		    appointment.setDescription(appointmentDto.getDescription());

		    // 🔥 Automatically PENDING
		    appointment.setStatus(AppointmentStatus.PENDING);
		    
		    Appointment saved =
		            appointmentRepository.save(appointment);
		    
		return modelMapper.map(saved, AppointmentDto.class);
	}

	@Override
	public AppointmentDto cancelAppointment(String appointmentId) {
		Appointment appointment = appointmentRepository.findById(appointmentId)
	            .orElseThrow(() -> new RuntimeException("Appointment not found"));

	    // Set status to CANCELLED
	    appointment.setStatus(AppointmentStatus.CANCELLED);

	    // Save updated appointment
	    appointment = appointmentRepository.save(appointment);
	    
		return modelMapper.map(appointment, AppointmentDto.class);
	}

	@Override
	public boolean confirmPayment(String orderId, String paymentId) {

	    Appointment appointment = appointmentRepository
	            .findByRazorpayOrderId(orderId)
	            .orElseThrow(() -> new RuntimeException("Appointment not found"));

	    appointment.setRazorpayPaymentId(paymentId);
	    appointment.setPaymentStatus(PaymentStatus.PAID);
	    appointment.setStatus(AppointmentStatus.SCHEDULED);

	    appointmentRepository.save(appointment);

	    return true;
	}

}
