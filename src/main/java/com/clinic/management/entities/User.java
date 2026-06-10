package com.clinic.management.entities;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.ToString.Exclude;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class User implements UserDetails{
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private String id;
	
	@Column(nullable = false,length = 30)
	private String username;
	
	@Column(nullable = false)
	private String password;
	
	@Column(nullable = false, length = 50)
	private String fullName;
	
	 @Column(nullable = false, unique = true)
	private String email;
	
	 @Column(nullable = false, length = 10)
	private String phoneNumber;
	
	@ManyToOne
	@JsonManagedReference
	@Exclude
	private Role role;
	
	@JsonManagedReference
	@OneToOne(mappedBy = "user")
	@ToString.Exclude
	private Doctor doctor;
	
	

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return List.of(new SimpleGrantedAuthority(role.getRoleName().toString()));
	}
	
	@Override
	public String getUsername() {
		
		return this.email;
	}
	
	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return this.password;
	}
	
	

}
