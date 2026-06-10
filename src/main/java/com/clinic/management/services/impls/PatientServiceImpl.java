package com.clinic.management.services.impls;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.clinic.management.dtos.DoctorDto;
import com.clinic.management.dtos.PatientDto;
import com.clinic.management.entities.Doctor;
import com.clinic.management.entities.Patient;
import com.clinic.management.entities.Role;
import com.clinic.management.entities.User;
import com.clinic.management.enums.AppRole;
import com.clinic.management.repositories.PatientRepository;
import com.clinic.management.repositories.RoleRepository;
import com.clinic.management.repositories.UserRepository;
import com.clinic.management.services.PatientService;


@Service
public class PatientServiceImpl implements PatientService{
	
	@Autowired
	private PatientRepository patientRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public PatientDto createPatient(PatientDto patientDto,String email) {
		
		
	User user=userRepository.findByEmail(email)
		.orElseThrow(()->new RuntimeException("User not found"));
	
	Role role =roleRepository.findByRoleName(AppRole.ROLE_PATIENT)
	.orElseThrow(()->new RuntimeException("Role Not Found"));
	
	user.setRole(role);
	User savedUser = userRepository.save(user);
	
	patientRepository.findByUserId(user.getId())
	.ifPresent(d->
	{
		throw new RuntimeException("User is already registered as a patient");
	});
	
	Patient patient = modelMapper.map(patientDto, Patient.class);
    patient.setUser(savedUser);
    Patient savedPatient = patientRepository.save(patient);
    
    
  
return modelMapper.map(savedPatient, PatientDto.class) ;
	
	
//		Patient patient = modelMapper.map(patientDto, Patient.class);
//		Patient savedPatient = patientRepository.save(pati);
//
//		Role role=roleRepository.findByRoleName(AppRole.ROLE_DOCTOR)
//				.orElseThrow(()->new RuntimeException("Role Not Found"));//orElseThrow beacuse of Optional 
//				
//				user.setRole(role);
//				
//				User savedUser=userRepository.save(user); //i
//		Role role=roleRepository.findByRoleName(AppRole.ROLE_PATIENT)
//		.orElseThrow(()->new RuntimeException("Role Not Found"));//orElseThrow beacuse of Optional 
//		
//		user.serRole(role);
//		
//		User savedUser=userRepository.save(patient); //i
		
		
	}

	@Override
	public PatientDto getPatientById(String id) {
		Patient patient = patientRepository.findByUserEmail(id)
		.orElseThrow(()->new RuntimeException("Patient Not Found"));
		return modelMapper.map(patient, PatientDto.class);
	}

	@Override
	public List<PatientDto> getAllPatients() {
		return patientRepository.findAll()
				.stream()
				.map(patient-> modelMapper.map(patient, PatientDto.class))
				.toList();
	}

	@Override
	public PatientDto updatePatient(String id, PatientDto patientDto) {
		Patient patient = patientRepository.findById(id)
		.orElseThrow(()-> new RuntimeException("Patient Not Found"));
		
		modelMapper.map(patientDto, patient);
		
		Patient savedPatient = patientRepository.save(patient);
		return modelMapper.map(savedPatient, PatientDto.class);
	}

	@Override
	public void deletePatient(String id) {
		 if (!patientRepository.existsById(id)) {
	            throw new RuntimeException("Patient not found with id: " + id);
	        }
		 patientRepository.deleteById(id);
		
	}

}
