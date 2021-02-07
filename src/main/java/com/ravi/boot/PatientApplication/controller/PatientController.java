package com.ravi.boot.PatientApplication.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.ravi.boot.PatientApplication.exception.ResourceNotFoundException;
import com.ravi.boot.PatientApplication.filestore.FileStorageService;
import com.ravi.boot.PatientApplication.filestore.ResponseFile;
import com.ravi.boot.PatientApplication.filestore.ResponseFileMessage;
import com.ravi.boot.PatientApplication.model.AuthenticationRequest;
import com.ravi.boot.PatientApplication.model.AuthenticationResponse;
import com.ravi.boot.PatientApplication.model.FileModel;
import com.ravi.boot.PatientApplication.model.MedicalHistory;
import com.ravi.boot.PatientApplication.model.Patient;
import com.ravi.boot.PatientApplication.service.MedicalHistoryService;
import com.ravi.boot.PatientApplication.service.MyUserDetailsService;
import com.ravi.boot.PatientApplication.service.PatientService;
import com.ravi.boot.PatientApplication.util.JwtUtil;

@RestController
public class PatientController {
	
	private static final Logger logger=LoggerFactory.getLogger(PatientController.class);
	
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
	
	@Autowired
	private FileStorageService fileStorageService;
	
	//Spring security authenticate method for generation of JWT
	@RequestMapping(value="/authenticate",method=RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception{
		logger.debug("Authenticating....");
		
		try{
			authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), 
							authenticationRequest.getPassword()));
		}
		catch(BadCredentialsException e) {
			logger.error("Not authenticated...");
			throw new ResourceNotFoundException("Incorrect username and password : "+e.getLocalizedMessage());
		}
		final UserDetails userDetails=userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
		final String jwt=jwtUtil.generateToken(userDetails);
		logger.debug("Authenticated...");		
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
	private ResponseEntity<Patient> getPatients(@PathVariable("patientId") long patientId) {
		Patient patient = patientService.getPatientById(patientId);
	    if(patient == null) {
	         throw new ResourceNotFoundException("Invalid patient id : " + patientId);
	    }
	    return new ResponseEntity<Patient>(patient, HttpStatus.OK);
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
	private ResponseEntity<Patient> savePatient(@RequestBody Patient patient) {
		Patient savedPatient = patientService.save(patient);
		return new ResponseEntity<Patient>(savedPatient, HttpStatus.OK);
	}

	// creating put mapping that updates the patient detail
	@PutMapping("/api/patients")
	private Patient updatePatient(@RequestBody Patient patient) {
		patientService.saveOrUpdate(patient);
		return patient;
	}
	
	@PostMapping("/upload")
	  public ResponseEntity<ResponseFileMessage> uploadFile(@RequestParam("file") MultipartFile file) {
	    String message = "";
	    try {
	    	fileStorageService.store(file);

	      message = "Uploaded the file successfully: " + file.getOriginalFilename();
	      return ResponseEntity.status(HttpStatus.OK).body(new ResponseFileMessage(message));
	    } catch (Exception e) {
	      message = "Could not upload the file: " + file.getOriginalFilename() + "!";
	      return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseFileMessage(message));
	    }
	  }

	  @GetMapping("/files")
	  public ResponseEntity<List<ResponseFile>> getListFiles() {
	    List<ResponseFile> files = fileStorageService.getAllFiles().map(dbFile -> {
	      String fileDownloadUri = ServletUriComponentsBuilder
	          .fromCurrentContextPath()
	          .path("/files/")
	          .path(dbFile.getId())
	          .toUriString();

	      return new ResponseFile(
	          dbFile.getName(),
	          fileDownloadUri,
	          dbFile.getType(),
	          dbFile.getData().length);
	    }).collect(Collectors.toList());

	    return ResponseEntity.status(HttpStatus.OK).body(files);
	  }

	  @GetMapping("/files/{id}")
	  public ResponseEntity<byte[]> getFile(@PathVariable String id) {
	    FileModel fileModel = fileStorageService.getFile(id);

	    return ResponseEntity.ok()
	        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileModel.getName() + "\"")
	        .body(fileModel.getData());
	  }
	

}
