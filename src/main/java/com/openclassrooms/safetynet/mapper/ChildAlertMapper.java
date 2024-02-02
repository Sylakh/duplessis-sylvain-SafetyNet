package com.openclassrooms.safetynet.mapper;

import org.springframework.stereotype.Component;

import com.openclassrooms.safetynet.DTO.ChildAlertDTO;
import com.openclassrooms.safetynet.model.MedicalRecord;

@Component
public class ChildAlertMapper {

	public ChildAlertDTO convertPatientToChildAlertDTO(MedicalRecord medicalRecord) {
		return new ChildAlertDTO(medicalRecord.getFirstName(), medicalRecord.getLastName(),
				medicalRecord.calculateAge(medicalRecord.getBirthDate()));
	}

}
