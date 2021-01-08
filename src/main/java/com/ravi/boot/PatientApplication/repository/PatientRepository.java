package com.ravi.boot.PatientApplication.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ravi.boot.PatientApplication.model.Patient;

public interface PatientRepository extends JpaRepository<Patient, Long>{

	
}
