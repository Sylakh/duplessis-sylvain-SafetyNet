package com.openclassrooms.safetynet.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.openclassrooms.safetynet.model.Medication;
import com.openclassrooms.safetynet.model.Patient;

@Repository
public interface MedicationRepository extends CrudRepository<Medication, Long> {

	void deleteByPatient(Patient patient);

	Optional<Medication> findByPatient(Patient patient);

	void deleteById(Long id);

	List<Medication> findAllByPatient(Patient patient);

}
