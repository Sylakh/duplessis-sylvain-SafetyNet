package com.openclassrooms.safetynet.controller;

import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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

	private static final Logger logger = LogManager.getLogger("FireStationController");

	@Autowired
	private FireStationService fireStationService;

	@GetMapping("/firestation")
	public Iterable<FireStation> GetAllAddress() {
		logger.info("Get all firestations from database");
		return fireStationService.getAllMapping();
	}

	@PostMapping("/firestation")
	public FireStation createMappingAddressWithStation(@RequestBody FireStation fireStation) {
		logger.info("Create a new mapping in database");
		return fireStationService.saveMapping(fireStation);
	}

	/**
	 * Put - Update the station of a specific address
	 * 
	 * @param FireStation object from request body
	 * @throws Exception
	 */
	@PutMapping("/firestation")
	public FireStation updateFireStationOfAnAddress(@RequestBody FireStation fireStation) throws Exception {
		logger.info("update process of the station of a specific address begins");
		Optional<FireStation> optionalFireStation = fireStationService
				.findFireStationOfAnAddress(fireStation.getAddress());
		if (optionalFireStation.isPresent()) {
			FireStation updatedFireStation = optionalFireStation.get();
			updatedFireStation.setStation(fireStation.getStation());
			logger.info("update done");
			return fireStationService.saveMapping(updatedFireStation);
		} else {
			logger.error("address not found");
			throw new Exception("Firestation not found (Put)");
		}
	}

	@DeleteMapping("/firestation")
	public void deleteAnAddressOrAStation(@RequestBody FireStation fireStation) throws Exception {
		if (fireStation.getAddress() != null) {
			logger.info("delete process begins from an address");
			Optional<FireStation> optionalFireStationFromAddress = fireStationService
					.findFireStationOfAnAddress(fireStation.getAddress());
			if (optionalFireStationFromAddress.isPresent()) {
				fireStationService.deleteByAddress(fireStation.getAddress());
				logger.info("delete process done for address:" + fireStation.getAddress());
			}
		} else if (fireStation.getStation() != null && fireStation.getAddress() == null) {
			logger.info("delete process begins from an address");
			Iterable<FireStation> iterableFireStationFromStation = fireStationService
					.findFireStationofAStation(fireStation.getStation());
			for (FireStation fireStationToDelete : iterableFireStationFromStation) {
				fireStationService.deleteByAddress(fireStationToDelete.getAddress());
				logger.info("delete process done for station" + fireStationToDelete.getStation() + " at address: "
						+ fireStationToDelete.getAddress());
			}
		} else {
			throw new Exception("FireStation not found (Delete)");
		}
	}
}
