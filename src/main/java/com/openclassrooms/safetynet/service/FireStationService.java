package com.openclassrooms.safetynet.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.openclassrooms.safetynet.DTO.FireStationDTO;
import com.openclassrooms.safetynet.mapper.FireStationMapper;
import com.openclassrooms.safetynet.model.FireStation;
import com.openclassrooms.safetynet.model.Person;
import com.openclassrooms.safetynet.repository.FireStationRepository;
import com.openclassrooms.safetynet.repository.PersonRepository;

import jakarta.transaction.Transactional;
import lombok.Data;

@Data
@Service
public class FireStationService {

	private static final Logger logger = LogManager.getLogger("FireStationService");

	@Autowired
	private FireStationRepository fireStationRepository;

	@Autowired
	private PersonRepository personRepository;

	@Autowired
	private FireStationMapper fireStationMapper;

	@Transactional
	public FireStationDTO createMapping(FireStation fireStation) {
		List<Person> persons = new ArrayList<>();
		persons = personRepository.findAllByAddress(fireStation.getAddress());
		fireStation.setPersons(persons);
		logger.info("creation of a new mapping done");
		return fireStationMapper.convertFireStationToFireStationDTO(fireStationRepository.save(fireStation));
	}

	@Transactional
	public FireStationDTO updateFireStation(@RequestBody FireStation fireStation) throws Exception {
		logger.info("update process of the station of a specific address begins");
		Optional<FireStation> optionalFireStation = fireStationRepository.findByAddress(fireStation.getAddress());
		if (optionalFireStation.isPresent()) {
			FireStation updatedFireStation = optionalFireStation.get();
			updatedFireStation.setStation(fireStation.getStation());
			logger.info("update done");
			return fireStationMapper.convertFireStationToFireStationDTO(fireStationRepository.save(updatedFireStation));
		} else {
			logger.error("address not found");
			throw new Exception("Firestation not found (Put)");
		}
	}

	@Transactional
	public void deleteFireStation(String address, String station) throws Exception {
		if (address != "") {
			logger.info("delete process begins from an address");
			Optional<FireStation> optionalFireStationFromAddress = fireStationRepository.findByAddress(address);
			if (optionalFireStationFromAddress.isPresent()) {
				FireStation fireStationFound = optionalFireStationFromAddress.get();
				fireStationRepository.deleteByAddress(fireStationFound.getAddress());
				logger.info("delete process done for address:" + address);
			}
		} else if (station != "" && address == "") {
			logger.info("delete process begins from an address");
			Iterable<FireStation> iterableFireStationFromStation = fireStationRepository.findByStation(station);
			for (FireStation fireStationToDelete : iterableFireStationFromStation) {
				fireStationRepository.deleteByAddress(fireStationToDelete.getAddress());
				logger.info("delete process done for station" + fireStationToDelete.getStation() + " at address: "
						+ fireStationToDelete.getAddress());
			}
		} else {
			logger.error("FireStation not found");
			throw new Exception("FireStation not found (Delete)");
		}
	}
}