package com.openclassrooms.safetynet.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.safetynet.DTO.FireStationDTO;
import com.openclassrooms.safetynet.model.FireStation;
import com.openclassrooms.safetynet.service.FireStationService;

@RestController
@RequestMapping("/firestation")
public class FireStationController {

	private static final Logger logger = LogManager.getLogger("FireStationController");

	@Autowired
	private FireStationService fireStationService;

	@PostMapping
	public FireStationDTO createMappingAddress(@RequestBody FireStation fireStation) {
		logger.info("Create a new mapping in database");
		return fireStationService.createMapping(fireStation);
	}

	@PutMapping
	public FireStationDTO updateFireStation(@RequestBody FireStation fireStation) throws Exception {
		logger.info("update process of the station of a specific address begins");
		return fireStationService.updateFireStation(fireStation);
	}

	@DeleteMapping
	public void deleteFireStation(@RequestParam String address, @RequestParam String station) throws Exception {
		logger.info("delete process of the station or an address  by requestbody begins");
		fireStationService.deleteFireStation(address, station);
	}
}
