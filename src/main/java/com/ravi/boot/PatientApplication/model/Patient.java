package com.ravi.boot.PatientApplication.model;

import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.ravi.boot.PatientApplication.audit.Auditable;

import io.swagger.annotations.ApiModel;

@Table(name ="Patient")
@Entity
@EntityListeners(AuditingEntityListener.class)
@ApiModel(value="This is patient clas")
public class Patient extends Auditable<String>  {

	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="patientId")
	private long id ;
	@Column(name="name")
	private String name;
	@Column(name="age")
	private int age;
	@Column(name="address")
	private String address;
	
	@OneToMany(mappedBy="patient")
	private Set<MedicalHistory> medicalHistories;

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
