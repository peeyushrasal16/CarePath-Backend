package com.clinic.management.security;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


import com.clinic.management.entities.User;
import com.clinic.management.exceptions.NotFoundException;
import com.clinic.management.repositories.UserRepository;


@Service
public class UserDetailsImpl implements UserDetailsService{
	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByEmail(username)
				.orElseThrow(()->new NotFoundException("User Not Found with email"));
//		.orElseThrow(()-> new NotFoundException("User Not Found with email"));
//	
	    System.out.println(user);
		return user;
	
	}

}



