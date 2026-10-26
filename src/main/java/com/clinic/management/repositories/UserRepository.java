package com.clinic.management.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.clinic.management.entities.User;
import java.util.List;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, String>{
	@Query("SELECT u FROM User u WHERE u.email = :email")
	Optional<User> findByEmail(String email); //if not present give error 
	Boolean existsByEmail(String email);
}
