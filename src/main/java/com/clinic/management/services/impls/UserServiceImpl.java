package com.clinic.management.services.impls;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.clinic.management.dtos.UserDto;
import com.clinic.management.entities.Role;
import com.clinic.management.entities.User;
import com.clinic.management.enums.AppRole;
import com.clinic.management.repositories.RoleRepository;
import com.clinic.management.repositories.UserRepository;
import com.clinic.management.services.UserService;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserRepository userRepository;
	//first complete roles
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public UserDto registerUser(UserDto userDto) {
		User user =modelMapper.map(userDto,User.class);
		
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		
		User savedUser =userRepository.save(user);
		
		
		return modelMapper.map(savedUser,UserDto.class);
	}

	@Override
	public UserDto registerPatient(UserDto userDto) {
		User patient = modelMapper.map(userDto, User.class);
		
		
		
		Role role = roleRepository.findByRoleName(AppRole.ROLE_PATIENT)
				.orElseThrow(()->new RuntimeException());
		patient.setRole(role);
		User savedPatient = userRepository.save(patient);
		return modelMapper.map(savedPatient, UserDto.class);
	}
	
	
	
	

}
