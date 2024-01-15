package com.openclassrooms.safetynet.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.openclassrooms.safetynet.model.Patient;
import com.openclassrooms.safetynet.repository.PatientRepository;

import jakarta.transaction.Transactional;
import lombok.Data;

@Data
@Service
public class PatientService {

	@Autowired
	private PatientRepository patientRepository;

	public Optional<Patient> findPatientByFirstNameAndLastName(String firstName, String lastName) {
		return patientRepository.findByFirstNameAndLastName(firstName, lastName);
	}

	@Transactional
	public void deletePatient(Long patientId) {
		Patient patient = patientRepository.findById(patientId).orElse(null);
		if (patient != null) {
			patientRepository.delete(patient);
		}
	}
}
