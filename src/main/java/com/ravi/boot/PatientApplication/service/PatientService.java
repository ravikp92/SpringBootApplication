package com.ravi.boot.PatientApplication.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ravi.boot.PatientApplication.model.Patient;
import com.ravi.boot.PatientApplication.repository.PatientRepository;

@Service
public class PatientService {

	@Autowired
	private PatientRepository patientRepository;

	public List<Patient> getAllPatients() {
		List<Patient> patients = new ArrayList<Patient>();
		patientRepository.findAll().forEach(p -> patients.add(p));
		return patients;
	}

	// getting a specific record by using the method findById() of CrudRepository
	public Patient getPatientById(long id) {
		return patientRepository.findById(id).get();
	}

	// saving a specific record by using the method save() of CrudRepository
	public void saveOrUpdate(Patient patient) {
		patientRepository.save(patient);
	}

	// deleting a specific record by using the method deleteById() of CrudRepository
	public void delete(long id) {
		patientRepository.deleteById(id);
	}

	// updating a record
	public void update(Patient patient, Long id) {
		Patient updatedpatient=getPatientById(id);
		updatedpatient.setAge(patient.getAge());
		updatedpatient.setMedicalHistories(patient.getMedicalHistories());
		updatedpatient.setAddress(patient.getAddress());
		patientRepository.save(updatedpatient);
	}

}
