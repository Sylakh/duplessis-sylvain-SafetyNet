package com.openclassrooms.safetynet.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "patient")
public class Patient {

	private static final Logger logger = LogManager.getLogger("model Patient");

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long patient_id;

	@Column(name = "first_name")
	private String firstName;

	@Column(name = "last_name")
	private String lastName;

	@Column(name = "birthdate")
	private String birthDate;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	@JoinColumn(name = "patient_id")
	List<Medication> medication = new ArrayList<>();

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	@JoinColumn(name = "patient_id")
	List<Allergy> allergy = new ArrayList<>();

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

	public Date convertStringToDate(String dateString) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		try {
			// Convertir la chaîne en objet Date
			Date date = dateFormat.parse(dateString);
			return date;
		} catch (ParseException e) {
			// Gérer l'exception si la conversion échoue
			logger.error("Conversion String to Date failed", e);
			;
			return null;
		}
	}

	public int calculateAge(String birthDate) {
		Date dateOfBirth = convertStringToDate(birthDate);

		if (dateOfBirth != null) {
			Calendar birthDateCal = Calendar.getInstance();
			birthDateCal.setTime(dateOfBirth);

			Calendar nowCal = Calendar.getInstance();

			int age = nowCal.get(Calendar.YEAR) - birthDateCal.get(Calendar.YEAR);

			// Vérifier si l'anniversaire n'a pas encore eu lieu cette année
			if (nowCal.get(Calendar.DAY_OF_YEAR) < birthDateCal.get(Calendar.DAY_OF_YEAR)) {
				age--;
			}

			return age;
		} else {
			logger.error("the calcul of Age failed!");
			return -1;
		}
	}
}
