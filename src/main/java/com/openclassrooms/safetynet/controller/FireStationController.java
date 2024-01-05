package com.openclassrooms.safetynet.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.safetynet.model.FireStation;
import com.openclassrooms.safetynet.service.FireStationService;

@RestController
public class FireStationController {

	@Autowired
	private FireStationService fireStationService;

	@GetMapping("/firestation")
	public Iterable<FireStation> GetAllAddress() {
		return fireStationService.getAllMapping();
	}

	@PostMapping("/firestation")
	public FireStation createMappingAddressWithStation(@RequestBody FireStation fireStation) {
		return fireStationService.saveMapping(fireStation);
	}

	@PutMapping("/firestation")
	public FireStation updateFireStationOfAnAddress(@RequestBody FireStation fireStation) {
		Optional<FireStation> optionalFireStation = fireStationService
				.updateFireStaionOfAnAddress(fireStation.getAddress());
		if (optionalFireStation.isPresent()) {
			FireStation updatedFireStation = optionalFireStation.get();
			updatedFireStation.setStation(fireStation.getStation());
			return fireStationService.saveMapping(updatedFireStation);
		} else {
			return null;
		}
	}

	@DeleteMapping("/firestation")
	public void deleteAnAddressOrAStation(@RequestBody FireStation fireStation) {
		if (fireStation.getStation() == null && fireStation.getAddress() != null) {
			fireStationService.deleteByAddress(fireStation.getAddress());
		} else if (fireStation.getStation() != null && fireStation.getAddress() == null) {
			fireStationService.deleteAllByStation(fireStation.getStation());
		} else {

		}
	}
}
