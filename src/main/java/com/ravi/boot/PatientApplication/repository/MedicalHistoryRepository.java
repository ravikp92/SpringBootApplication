package com.ravi.boot.PatientApplication.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ravi.boot.PatientApplication.model.MedicalHistory;

public interface MedicalHistoryRepository extends JpaRepository<MedicalHistory, Long>{

}
