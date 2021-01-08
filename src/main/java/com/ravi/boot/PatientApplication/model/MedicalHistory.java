package com.ravi.boot.PatientApplication.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.ravi.boot.PatientApplication.audit.Auditable;

import io.swagger.annotations.ApiModel;

@Table(name ="MedicalHistory")
@Entity
@EntityListeners(AuditingEntityListener.class)
@ApiModel(value="This is patient's  MedicalHistory class")
public class MedicalHistory extends Auditable<String> {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="medicalHistoryId")
	private long id;
	@Column(name="diseaseName")
	private String diseaseName;
	@Column(name="fromDate")
	private LocalDate fromDate;
	@Column(name="ToDate")
	private LocalDate ToDate;
	
	@ManyToOne
	@JoinColumn(name="patientId", nullable=false)
	private Patient patient;
	
	public MedicalHistory() {
	}


	public MedicalHistory(long id, String diseaseName, LocalDate fromDate, LocalDate toDate, Patient patient) {
		super();
		this.id = id;
		this.diseaseName = diseaseName;
		this.fromDate = fromDate;
		ToDate = toDate;
		this.patient = patient;
	}


	public long getId() {
		return id;
	}


	public void setId(long id) {
		this.id = id;
	}


	public String getDiseaseName() {
		return diseaseName;
	}


	public void setDiseaseName(String diseaseName) {
		this.diseaseName = diseaseName;
	}


	public LocalDate getFromDate() {
		return fromDate;
	}


	public void setFromDate(LocalDate fromDate) {
		this.fromDate = fromDate;
	}


	public LocalDate getToDate() {
		return ToDate;
	}


	public void setToDate(LocalDate toDate) {
		ToDate = toDate;
	}


	public Patient getPatient() {
		return patient;
	}


	public void setPatient(Patient patient) {
		this.patient = patient;
	}


	@Override
	public String toString() {
		return "MedicalHistory [id=" + id + ", diseaseName=" + diseaseName + ", fromDate=" + fromDate + ", ToDate="
				+ ToDate + ", patient=" + patient + "]";
	}
	
	
}
