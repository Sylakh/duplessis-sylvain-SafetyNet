package com.openclassrooms.safetynet.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.openclassrooms.safetynet.model.Medication;
import com.openclassrooms.safetynet.model.Patient;
import com.openclassrooms.safetynet.repository.MedicationRepository;

import jakarta.transaction.Transactional;
import lombok.Data;

@Data
@Service
public class MedicationService {

	@Autowired
	private MedicationRepository medicationRepository;

	@Transactional
	public void deleteMedicationById(Long id) {
		medicationRepository.deleteById(id);
	}

	public List<Medication> findAllMedicationByPatient(Patient patient) {
		return medicationRepository.findAllByPatient(patient);
	}

}
