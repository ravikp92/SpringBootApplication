package com.ravi.boot.PatientApplication.model;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Range;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.ravi.boot.PatientApplication.audit.Auditable;

import io.swagger.annotations.ApiModel;

@Table(name ="Patient")
@Entity
@EntityListeners(AuditingEntityListener.class)
@ApiModel(value="This is patient class")
public class Patient extends Auditable<String>  {

	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="patientId")
	private long id ;
	
	
	@Column(name="name")
	@Size(max = 20, min = 3, message = "{patient.name.invalid}")
	@NotEmpty(message = "Name must not be empty")
	private String name;
	
	@Column(name="age")
	@NotEmpty(message = "Age must not be empty")
	@Range(min=18, max=55)
	private int age;
	
	@Column(name="address")
	private String address;
	
	@OneToMany(cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,mappedBy="patient")
	private Set<MedicalHistory> medicalHistories =new HashSet<MedicalHistory>();

	public Patient() {
	}

	public Patient(long id, String name, int age, String address, Set<MedicalHistory> medicalHistories) {
		super();
		this.id = id;
		this.name = name;
		this.age = age;
		this.address = address;
		this.medicalHistories = medicalHistories;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Set<MedicalHistory> getMedicalHistories() {
		return medicalHistories;
	}

	public void setMedicalHistories(Set<MedicalHistory> medicalHistories) {
		this.medicalHistories = medicalHistories;
	}

	@Override
	public String toString() {
		return "Patient [id=" + id + ", name=" + name + ", age=" + age + ", address=" + address + ", medicalHistories="
				+ medicalHistories + "]";
	}
	
	
	
	
}
