package com.openclassrooms.safetynet.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.openclassrooms.safetynet.model.Allergy;
import com.openclassrooms.safetynet.model.Patient;

@Repository
public interface AllergyRepository extends CrudRepository<Allergy, Long> {

	void deleteById(Long id);

	// Optional<Allergy> findByPatient(Patient patient);

	List<Allergy> findAllByPatient(Patient patient);

}
