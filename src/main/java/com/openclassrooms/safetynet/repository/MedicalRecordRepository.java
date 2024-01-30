package com.openclassrooms.safetynet.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.openclassrooms.safetynet.model.MedicalRecord;

@Repository
public interface MedicalRecordRepository extends CrudRepository<MedicalRecord, Long> {

	public Optional<MedicalRecord> findByFirstNameAndLastName(String firstName, String lastName);

}
