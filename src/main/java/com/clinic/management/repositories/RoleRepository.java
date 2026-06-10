package com.clinic.management.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.clinic.management.entities.Role;
import java.util.List;
import java.util.Optional;

import com.clinic.management.enums.AppRole;


public interface RoleRepository extends JpaRepository<Role, Integer>{
	Optional<Role> findByRoleName(AppRole roleName);

}
