package com.clinic.management.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.clinic.management.entities.User;
import java.util.List;


public interface UserRepository extends JpaRepository<User, String>{
	Optional<User> findByEmail(String email); //if not present give error 
	Boolean existsByEmail(String email);
}
