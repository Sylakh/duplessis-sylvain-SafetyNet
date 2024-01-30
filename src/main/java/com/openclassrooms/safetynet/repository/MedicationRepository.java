package com.openclassrooms.safetynet.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.openclassrooms.safetynet.model.MedicalRecord;
import com.openclassrooms.safetynet.model.Medication;

@Repository
public interface MedicationRepository extends CrudRepository<Medication, Long> {

	List<Medication> findAllByMedicalRecord(MedicalRecord medicalRecord);

	void deleteAllByMedicalRecord(MedicalRecord medicalRecord);

}
