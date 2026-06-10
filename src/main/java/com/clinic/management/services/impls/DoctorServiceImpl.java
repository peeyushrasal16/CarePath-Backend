package com.clinic.management.services.impls;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.clinic.management.dtos.DoctorDto;
import com.clinic.management.entities.Doctor;
import com.clinic.management.entities.Role;
import com.clinic.management.entities.User;
import com.clinic.management.enums.AppRole;
import com.clinic.management.repositories.DoctorRepository;
import com.clinic.management.repositories.RoleRepository;
import com.clinic.management.repositories.UserRepository;
import com.clinic.management.services.DoctorService;

@Service
public class DoctorServiceImpl implements DoctorService{
	
	@Autowired
	private  DoctorRepository doctorRepository;
	@Autowired
    private  UserRepository userRepository;
	@Autowired
    private ModelMapper modelMapper;
	
	@Autowired
	private RoleRepository roleRepository;
	
	

	@Override
	public DoctorDto createDoctor(DoctorDto doctorDto,String email) {
		if (doctorRepository.existsByLicenseNumber(doctorDto.getLicenseNumber())) {
            throw new RuntimeException("Doctor with this license number already exists");
        }

        // Fetch user
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Doctor not found"));
        
        

		Role role=roleRepository.findByRoleName(AppRole.ROLE_DOCTOR)
		.orElseThrow(()->new RuntimeException("Role Not Found"));//orElseThrow beacuse of Optional 
		
		user.setRole(role);
		
		User savedUser=userRepository.save(user); //i
        
        doctorRepository.findByUserId(user.getId())
        .ifPresent(d -> {
            throw new RuntimeException("User is already registered as a doctor");
        });

              Doctor doctor = modelMapper.map(doctorDto, Doctor.class);
               doctor.setUser(user);
             Doctor savedDoctor = doctorRepository.save(doctor);
          DoctorDto responseDto = modelMapper.map(savedDoctor, DoctorDto.class);
            responseDto.setId(savedDoctor.getUser().getId());
		return responseDto;
	}

	
	
	@Override
	public DoctorDto getDoctorById(String doctorId) {
		Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new RuntimeException("Doctor not found"));

        DoctorDto dto = modelMapper.map(doctor, DoctorDto.class);
        dto.setId(doctor.getUser().getId());
        return dto;
    }
	

	@Override
	public DoctorDto getDoctorByUserId(String userId) {
		 Doctor doctor = doctorRepository.findByUserId(userId)
	                .orElseThrow(() -> new RuntimeException("Doctor not found for user"));

	        DoctorDto dto = modelMapper.map(doctor, DoctorDto.class);
	        dto.setId(doctor.getUser().getId());
	        return dto;
	}

	@Override
	public List<DoctorDto> getAllDoctors() {
		return doctorRepository.findAll()
                .stream()
                .map(doctor -> modelMapper.map(doctor, DoctorDto.class))
                .toList();
//                .map(doctor -> {q2a1	
//                    DoctorDto dto = modelMapper.map(doctor, DoctorDto.class);
//                    dto.setUserId(doctor.getUser().getId());
//                    return dto;
//                })
//                .collect(Collectors.toList());
	}

	@Override
	public List<DoctorDto> getDoctorsBySpecialization(String specialization) {
		return doctorRepository.findBySpecialization(specialization)
                .stream()
                .map(doctor -> {
                    DoctorDto dto = modelMapper.map(doctor, DoctorDto.class);
                    dto.setId(doctor.getUser().getId());
                    return dto;
                })
                .collect(Collectors.toList());
	}

	@Override
	public List<DoctorDto> getDoctorsByExperience(int years) {
		return doctorRepository.findByYearsOfExperienceGreaterThanEqual(years)
                .stream()
                .map(doctor -> {
                    DoctorDto dto = modelMapper.map(doctor, DoctorDto.class);
                    dto.setId(doctor.getUser().getId());
                    return dto;
                })
                .collect(Collectors.toList());
	}

	@Override
	public DoctorDto updateDoctor(String doctorId, DoctorDto doctorDto) {
		Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new RuntimeException("Doctor not found"));

        doctor.setSpecialization(doctorDto.getSpecialization());
        doctor.setYearsOfExperience(doctorDto.getYearsOfExperience());
        doctor.setQualification(doctorDto.getQualification());

        // Update license number if changed
        if (!doctor.getLicenseNumber().equals(doctorDto.getLicenseNumber())) {
            if (doctorRepository.existsByLicenseNumber(doctorDto.getLicenseNumber())) {
                throw new RuntimeException("License number already in use");
            }
            doctor.setLicenseNumber(doctorDto.getLicenseNumber());
        }

        Doctor updatedDoctor = doctorRepository.save(doctor);

        DoctorDto dto = modelMapper.map(updatedDoctor, DoctorDto.class);
        dto.setId(updatedDoctor.getUser().getId());
        return dto;
	}

	@Override
	public void deleteDoctor(String doctorId) {
		Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new RuntimeException("Doctor not found"));
        doctorRepository.delete(doctor);
		
	}


	

}
