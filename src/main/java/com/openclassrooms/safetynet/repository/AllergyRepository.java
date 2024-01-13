package com.openclassrooms.safetynet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.openclassrooms.safetynet.model.Allergy;

@Repository
public interface AllergyRepository extends JpaRepository<Allergy, Long> {

}
