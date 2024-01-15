package com.openclassrooms.safetynet.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.openclassrooms.safetynet.model.Patient;

@Repository
public interface PatientRepository extends CrudRepository<Patient, Long> {

	public Optional<Patient> findByFirstNameAndLastName(String firstName, String lastName);

}
