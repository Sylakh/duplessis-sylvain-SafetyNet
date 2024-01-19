package com.openclassrooms.safetynet.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.safetynet.DTO.FireStationDTO;
import com.openclassrooms.safetynet.model.FireStation;
import com.openclassrooms.safetynet.service.FireStationService;

@RestController
public class FireStationController {

	private static final Logger logger = LogManager.getLogger("FireStationController");

	@Autowired
	private FireStationService fireStationService;

	@GetMapping("/firestation")
	public List<FireStationDTO> GetAllAddress() {
		logger.info("Get all firestations from database");
		List<FireStationDTO> listFireStationDTO = fireStationService.getAllAddress();
		return listFireStationDTO;
	}

	@PostMapping("/firestation")
	public FireStationDTO createMappingAddressWithStation(@RequestBody FireStation fireStation) {
		logger.info("Create a new mapping in database");
		return fireStationService.createMappingAddressWithStation(fireStation);
	}

	@PutMapping("/firestation")
	public FireStationDTO updateFireStationOfAnAddress(@RequestBody FireStation fireStation) throws Exception {
		logger.info("update process of the station of a specific address begins");
		return fireStationService.updateFireStationOfAnAddress(fireStation);
	}

	@DeleteMapping("/firestation")
	public void deleteAnAddressOrAStation(@RequestBody FireStation fireStation) throws Exception {
		logger.info("delete process of the station or an address  by requestbody begins");
		fireStationService.deleteAnAddressOrAStation(fireStation);
	}
}
