package com.openclassrooms.safetynet.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.openclassrooms.safetynet.model.Allergy;
import com.openclassrooms.safetynet.model.MedicalRecord;

@Repository
public interface AllergyRepository extends CrudRepository<Allergy, Long> {

	List<Allergy> findAllByMedicalRecord(MedicalRecord medicalRecord);

	void deleteAllByMedicalRecord(MedicalRecord medicalRecord);

}
