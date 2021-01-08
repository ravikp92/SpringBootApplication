package com.ravi.boot.PatientApplication.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ravi.boot.PatientApplication.model.MedicalHistory;
import com.ravi.boot.PatientApplication.repository.MedicalHistoryRepository;

@Service
public class MedicalHistoryService {
	
	@Autowired
	private MedicalHistoryRepository medicalHistoryRepo;

	public List<MedicalHistory> getAllMedicalHistory() {
		List<MedicalHistory> medHistories = new ArrayList<MedicalHistory>();
		medicalHistoryRepo.findAll().forEach(p -> medHistories.add(p));
		return medHistories;
	}

	// getting a specific record by using the method findById() of CrudRepository
	public MedicalHistory getMedicalHistoryById(long id) {
		return medicalHistoryRepo.findById(id).get();
	}

	// saving a specific record by using the method save() of CrudRepository
	public void saveOrUpdate(MedicalHistory medicalHistory) {
		medicalHistoryRepo.save(medicalHistory);
	}

	// deleting a specific record by using the method deleteById() of CrudRepository
	public void delete(long id) {
		medicalHistoryRepo.deleteById(id);
	}

	// updating a record
	public void update(MedicalHistory medicalHistory, Long id) {
		MedicalHistory updatedMedicalHistory=getMedicalHistoryById(id);
		updatedMedicalHistory.setDiseaseName(medicalHistory.getDiseaseName());
		updatedMedicalHistory.setFromDate(medicalHistory.getFromDate());
		updatedMedicalHistory.setToDate(medicalHistory.getToDate());
		updatedMedicalHistory.setPatient(medicalHistory.getPatient());
		medicalHistoryRepo.save(updatedMedicalHistory);
	}

}
