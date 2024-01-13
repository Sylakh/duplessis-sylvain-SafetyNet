package com.openclassrooms.safetynet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.openclassrooms.safetynet.model.Medication;

@Repository
public interface MedicationRepository extends JpaRepository<Medication, Long> {

}
