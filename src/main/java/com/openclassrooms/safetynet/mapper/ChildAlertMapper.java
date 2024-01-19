package com.openclassrooms.safetynet.mapper;

import org.springframework.stereotype.Component;

import com.openclassrooms.safetynet.DTO.ChildAlertDTO;
import com.openclassrooms.safetynet.model.Patient;

@Component
public class ChildAlertMapper {

	public ChildAlertDTO convertPatientToChildAlertDTO(Patient patient) {
		return new ChildAlertDTO(patient.getFirstName(), patient.getLastName(),
				patient.calculateAge(patient.getBirthDate()));
	}

}
