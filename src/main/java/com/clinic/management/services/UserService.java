package com.clinic.management.services;

import com.clinic.management.dtos.UserDto;

public interface UserService {
	UserDto registerUser(UserDto userDto);
	UserDto registerPatient(UserDto userDto);

}
