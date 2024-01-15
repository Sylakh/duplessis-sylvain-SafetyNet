package com.openclassrooms.safetynet.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "allergie")
public class Allergy {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "allergy_id")
	private Long id;

	@ManyToOne
	@JoinColumn(name = "patient_id")
	private Patient patient;

	@Column(name = "allergy_name")
	private String allergyName;

	public Long getAllergy_id() {
		return id;
	}

	public void setAllergy_id(Long id) {
		this.id = id;
	}

	public Patient getPatient() {
		return patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}

	public String getAllergyName() {
		return allergyName;
	}

	public void setAllergyName(String allergyName) {
		this.allergyName = allergyName;
	}

}
