package com.openclassrooms.safetynet.mapper;

import org.springframework.stereotype.Component;

import com.openclassrooms.safetynet.DTO.FireStationDTO;
import com.openclassrooms.safetynet.model.FireStation;

@Component
public class FireStationMapper {

	public FireStationDTO convertFireStationToFireStationDTO(FireStation fireStation) {
		return new FireStationDTO(fireStation.getAddress(), fireStation.getStation());
	}

}
