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
import com.openclassrooms.safetynet.repository.FireStationRepository;

import jakarta.transaction.Transactional;
import lombok.Data;

@Data
@Service
public class FireStationService {

	private static final Logger logger = LogManager.getLogger("FireStationService");

	@Autowired
	private FireStationRepository fireStationRepository;

	@Autowired
	private FireStationMapper fireStationMapper;

	public FireStation saveMapping(FireStation fireStation) {
		FireStation savedFireStation = fireStationRepository.save(fireStation);
		return savedFireStation;
	}

	public Optional<FireStation> findFireStationOfAnAddress(String address) {
		return fireStationRepository.findByAddress(address);
	}

	public List<FireStation> findFireStationofAStation(String station) {
		return fireStationRepository.findByStation(station);
	}

	@Transactional
	public void deleteByAddress(String address) {
		fireStationRepository.deleteByAddress(address);
	}

	public List<FireStation> getAllAddress() {
		List<FireStation> listFireStation = (List<FireStation>) fireStationRepository.findAll();
		List<FireStationDTO> listFireStationDTO = new ArrayList<>();
		for (FireStation fireStation : listFireStation) {
			listFireStationDTO.add(fireStationMapper.convertFireStationToFireStationDTO(fireStation));
		}
		return listFireStation;
	}

	public FireStationDTO createMappingAddressWithStation(FireStation fireStation) {
		return fireStationMapper.convertFireStationToFireStationDTO(fireStationRepository.save(fireStation));
	}

	public FireStationDTO updateFireStationOfAnAddress(@RequestBody FireStation fireStation) throws Exception {
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
	public void deleteAnAddressOrAStation(FireStation fireStation) throws Exception {
		if (fireStation.getAddress() != null) {
			logger.info("delete process begins from an address");
			Optional<FireStation> optionalFireStationFromAddress = fireStationRepository
					.findByAddress(fireStation.getAddress());
			if (optionalFireStationFromAddress.isPresent()) {
				FireStation fireStationFound = optionalFireStationFromAddress.get();
				fireStationRepository.deleteByAddress(fireStationFound.getAddress());
				logger.info("delete process done for address:" + fireStation.getAddress());
			}
		} else if (fireStation.getStation() != null && fireStation.getAddress() == null) {
			logger.info("delete process begins from an address");
			Iterable<FireStation> iterableFireStationFromStation = fireStationRepository
					.findByStation(fireStation.getStation());
			for (FireStation fireStationToDelete : iterableFireStationFromStation) {
				fireStationRepository.deleteByAddress(fireStationToDelete.getAddress());
				logger.info("delete process done for station" + fireStationToDelete.getStation() + " at address: "
						+ fireStationToDelete.getAddress());
			}
		} else {
			throw new Exception("FireStation not found (Delete)");
		}
	}
}