package com.openclassrooms.safetynet.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "medicalrecord")
public class MedicalRecord {

	private static final Logger logger = LogManager.getLogger("model medicalrecord");

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "medical_record_id")
	private Long medicalRecordId;

	@Column(name = "firstname")
	private String firstName;

	@Column(name = "lastname")
	private String lastName;

	@Column(name = "name")
	private String name;

	@Column(name = "birthdate")
	private String birthDate;

	@OneToMany(mappedBy = "medicalRecord", cascade = CascadeType.ALL, orphanRemoval = false)
	@JsonManagedReference
	List<Medication> medications = new ArrayList<>();

	@OneToMany(mappedBy = "medicalRecord", cascade = CascadeType.ALL, orphanRemoval = false)
	@JsonManagedReference
	List<Allergy> allergies = new ArrayList<>();

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "person_id")
	@JsonBackReference
	private Person person;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Medication> getMedications() {
		return medications;
	}

	public void setMedications(List<Medication> medications) {
		this.medications = medications;
	}

	public List<Allergy> getAllergies() {
		return allergies;
	}

	public void setAllergies(List<Allergy> allergies) {
		this.allergies = allergies;
	}

	public Long getMedicalRecordId() {
		return medicalRecordId;
	}

	public void setMedicalRecordId(Long medicalRecordId) {
		this.medicalRecordId = medicalRecordId;
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

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public static Logger getLogger() {
		return logger;
	}

	public Date convertStringToDate(String dateString) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		try {
			// Convertir la chaîne en objet Date
			Date date = dateFormat.parse(dateString);
			return date;
		} catch (ParseException e) {
			// Gérer l'exception si la conversion échoue
			logger.error("Conversion String to Date failed", e);
			throw new IllegalArgumentException("Date: " + dateString + " should be of the format : MM/dd/yyyy");
		}
	}

	public int calculateAge(String birthDate) {
		/**
		 * Date dateOfBirth = convertStringToDate(birthDate);
		 * 
		 * if (dateOfBirth != null) { Calendar birthDateCal = Calendar.getInstance();
		 * birthDateCal.setTime(dateOfBirth);
		 * 
		 * Calendar nowCal = Calendar.getInstance();
		 * 
		 * int age = nowCal.get(Calendar.YEAR) - birthDateCal.get(Calendar.YEAR);
		 * 
		 * // Vérifier si l'anniversaire n'a pas encore eu lieu cette année if
		 * (nowCal.get(Calendar.DAY_OF_YEAR) < birthDateCal.get(Calendar.DAY_OF_YEAR)) {
		 * age--; }
		 * 
		 * return age; } else { logger.error("the calcul of Age failed!"); return -1; }
		 */
		Date dateOfBirth = convertStringToDate(birthDate);
		Calendar birthDateCal = Calendar.getInstance();
		birthDateCal.setTime(dateOfBirth);

		Calendar nowCal = Calendar.getInstance();

		int age = nowCal.get(Calendar.YEAR) - birthDateCal.get(Calendar.YEAR);

		// Vérifier si l'anniversaire n'a pas encore eu lieu cette année
		if (nowCal.get(Calendar.DAY_OF_YEAR) < birthDateCal.get(Calendar.DAY_OF_YEAR)) {
			age--;
		}

		return age;

	}
}
