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
@Table(name = "allergy")
public class Allergy {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "allergy_id")
	private Long id;

	@ManyToOne
	@JoinColumn(name = "medical_record_id")
	private MedicalRecord medicalRecord;

	@Column(name = "allergy_name")
	private String allergyName;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public MedicalRecord getMedicalRecord() {
		return medicalRecord;
	}

	public void setMedicalRecord(MedicalRecord medicalRecord) {
		this.medicalRecord = medicalRecord;
	}

	public String getAllergyName() {
		return allergyName;
	}

	public void setAllergyName(String allergyName) {
		this.allergyName = allergyName;
	}

}
