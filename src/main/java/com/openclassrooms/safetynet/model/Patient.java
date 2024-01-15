package com.openclassrooms.safetynet.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "patient")
public class Patient {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long patient_id;

	@Column(name = "first_name")
	private String firstName;

	@Column(name = "last_name")
	private String lastName;

	@Column(name = "birthdate")
	private String birthDate;

	// @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL, orphanRemoval =
	// true)
	// private List<Medication> medications = new ArrayList<>();

	// @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL, orphanRemoval =
	// true)
	// private List<Allergy> allergies = new ArrayList<>();

	// Getter and setter methods
	/**
	 * public void addMedication(Medication medication) {
	 * medications.add(medication); medication.setPatient(this); }
	 * 
	 * public void removeAllMedications() { medications.forEach(medication ->
	 * medication.setPatient(null)); medications.clear(); }
	 * 
	 * public void addAllergy(Allergy allergy) { allergies.add(allergy);
	 * allergy.setPatient(this); }
	 * 
	 * public void removeAllAllergies() { allergies.forEach(allergy ->
	 * allergy.setPatient(null)); allergies.clear(); }
	 */
	public Long getPatient_id() {
		return patient_id;
	}

	public void setPatient_id(Long patient_id) {
		this.patient_id = patient_id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}

}
