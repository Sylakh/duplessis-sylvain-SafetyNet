package com.openclassrooms.safetynet.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.openclassrooms.safetynet.model.FireStation;

@Repository
public interface FireStationRepository extends CrudRepository<FireStation, String> {

	Optional<FireStation> findByAddress(String address);

	List<FireStation> findByStation(String station);

	void deleteByAddress(String address);

	List<FireStation> findAllByStation(String station);

}
