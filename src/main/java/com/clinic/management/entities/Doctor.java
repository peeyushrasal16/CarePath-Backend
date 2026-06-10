package com.clinic.management.entities;

import java.awt.List;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Doctor {
	@Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

	
    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonBackReference
    @ToString.Exclude
    private User user;
    
   
    private String doctorName;
    private String specialization;

    private int yearsOfExperience;

    private String licenseNumber;

    private String qualification;
    
  
    
}


