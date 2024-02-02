package com.openclassrooms.safetynet.mapper;

import org.springframework.stereotype.Component;

import com.openclassrooms.safetynet.DTO.PersonInfoDTO;
import com.openclassrooms.safetynet.model.Allergy;
import com.openclassrooms.safetynet.model.Medication;
import com.openclassrooms.safetynet.model.Person;

@Component
public class PersonInfoDTOMapper {

	public PersonInfoDTO convertPersonToPersonInfoDTO(Person person) {

		String medications = "";
		String allergies = "";
		for (Medication medication : person.getMedicalRecord().getMedication()) {
			if (medications == "") {
				medications = medication.getMedication();
			} else {
				medications = medications + ", " + medication.getMedication();
			}
		}
		for (Allergy allergy : person.getMedicalRecord().getAllergy()) {
			if (allergies == "") {
				allergies = allergy.getAllergyName();
			} else {
				allergies = allergies + ", " + allergy.getAllergyName();
			}
		}
		int age = person.getMedicalRecord().calculateAge(person.getMedicalRecord().getBirthDate());
		return new PersonInfoDTO(person.getFirstName(), person.getLastName(), person.getAddress(), person.getCity(),
				age, medications, allergies);
	}

}
