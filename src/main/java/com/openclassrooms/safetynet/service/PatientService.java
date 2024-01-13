package com.openclassrooms.safetynet.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.openclassrooms.safetynet.model.Patient;
import com.openclassrooms.safetynet.repository.AllergyRepository;
import com.openclassrooms.safetynet.repository.MedicationRepository;
import com.openclassrooms.safetynet.repository.PatientRepository;

@Service
public class PatientService {

	@Autowired
	private PatientRepository patientRepository;

	@Autowired
	private MedicationRepository medicationRepository;

	@Autowired
	private AllergyRepository allergyRepository;

	public List<Patient> getAllPatient() {
		return patientRepository.findAll();
	}
}
