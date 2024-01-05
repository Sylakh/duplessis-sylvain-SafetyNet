package com.openclassrooms.safetynet.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.openclassrooms.safetynet.model.FireStation;
import com.openclassrooms.safetynet.repository.FireStationRepository;

import lombok.Data;

@Data
@Service
public class FireStationService {

	@Autowired
	private FireStationRepository fireStationRepository;

	public Iterable<FireStation> getAllMapping() {
		return fireStationRepository.findAll();
	}

	public FireStation saveMapping(FireStation fireStation) {
		FireStation savedFireStation = fireStationRepository.save(fireStation);
		return savedFireStation;
	}

	public Optional<FireStation> updateFireStaionOfAnAddress(String address) {
		return fireStationRepository.findByAddress(address);
	}

	public void deleteByAddress(String address) {
		fireStationRepository.deleteByAddress(address);
	}

	public void deleteAllByStation(String station) {
		fireStationRepository.deleteAllByStation(station);
	}
}