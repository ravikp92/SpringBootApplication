package com.ravi.boot.PatientApplication.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ravi.boot.PatientApplication.model.AuthenticationRequest;
import com.ravi.boot.PatientApplication.model.AuthenticationResponse;
import com.ravi.boot.PatientApplication.model.MedicalHistory;
import com.ravi.boot.PatientApplication.model.Patient;
import com.ravi.boot.PatientApplication.service.MedicalHistoryService;
import com.ravi.boot.PatientApplication.service.MyUserDetailsService;
import com.ravi.boot.PatientApplication.service.PatientService;
import com.ravi.boot.PatientApplication.util.JwtUtil;

@RestController
public class PatientController {

	// autowire the PatientService class
	@Autowired
	private PatientService patientService;

	@Autowired
	private MedicalHistoryService medicalHistoryService;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private MyUserDetailsService userDetailsService;
	
	@Autowired
	JwtUtil jwtUtil;
	
	//Spring security authenticate method for generation of JWT
	@RequestMapping(value="/authenticate",method=RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception{
		try{
			authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), 
							authenticationRequest.getPassword()));
		}
		catch(BadCredentialsException e) {
			throw new Exception("Incorrect username and password : ",e);
		}
		final UserDetails userDetails=userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
		final String jwt=jwtUtil.generateToken(userDetails);
		return ResponseEntity.ok(new AuthenticationResponse(jwt));
	}
	
	
	// creating a get mapping that retrieves all the patient detail from the
	// database
	@GetMapping("/api/patients")
	private List<Patient> getAllPatients() {
		return patientService.getAllPatients();
	}

	// creating a get mapping that retrieves the detail of a specific patient
	@GetMapping("/api/patients/{patientId}")
	private Patient getPatients(@PathVariable("patientId") long patientId) {
		return patientService.getPatientById(patientId);
	}

	// creating a get mapping that retrieves the detail of a specific patient
	@GetMapping("/api/patients/{patientId}/medicalHistory/{medId}")
	private MedicalHistory getMedicalHistoryOfPatient(@PathVariable("patientId") long patientId, @PathVariable("medId") long medId) {
		long medIdFound = 0l;
		Patient patient = patientService.getPatientById(patientId);
		for (MedicalHistory medHistory : patient.getMedicalHistories()) {
			if (medHistory.getId() == medId) {
				medIdFound = medId;
				break;
			}
		}
		return medicalHistoryService.getMedicalHistoryById(medIdFound);
	}

	// creating a delete mapping that deletes a specified patient
	@DeleteMapping("/api/patients/{patientId}")
	private void deletePatient(@PathVariable("patientId") long patientId) {
		patientService.delete(patientId);
	}

	// creating post mapping that post the patient detail in the database
	@PostMapping("/api/patients")
	private long savePatient(@RequestBody Patient patient) {
		patientService.saveOrUpdate(patient);
		return patient.getId();
	}

	// creating put mapping that updates the patient detail
	@PutMapping("/api/patients")
	private Patient updatePatient(@RequestBody Patient patient) {
		patientService.saveOrUpdate(patient);
		return patient;
	}

}
