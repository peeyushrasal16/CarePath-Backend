package com.clinic.management.dtos;

import com.clinic.management.entities.User;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DoctorDto {
	

private String id;

private String doctorName;
    

    private String specialization;

    private int yearsOfExperience;

    private String licenseNumber;

    private String qualification;
	

}
