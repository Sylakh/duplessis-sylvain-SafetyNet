package com.openclassrooms.safetynet.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.openclassrooms.safetynet.model.Allergy;
import com.openclassrooms.safetynet.model.Patient;
import com.openclassrooms.safetynet.repository.AllergyRepository;

import jakarta.transaction.Transactional;
import lombok.Data;

@Data
@Service
public class AllergyService {

	@Autowired
	private AllergyRepository allergyRepository;

	@Transactional
	public void deleteAllergyById(Long Id) {
		allergyRepository.deleteById(Id);
	}

	public List<Allergy> findAllAllergyByPatient(Patient patient) {
		return allergyRepository.findAllByPatient(patient);
	}

}
