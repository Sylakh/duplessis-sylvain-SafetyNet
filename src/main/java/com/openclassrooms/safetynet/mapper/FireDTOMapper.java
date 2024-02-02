package com.openclassrooms.safetynet.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.openclassrooms.safetynet.DTO.FireDTO;
import com.openclassrooms.safetynet.model.Allergy;
import com.openclassrooms.safetynet.model.FireStation;
import com.openclassrooms.safetynet.model.Medication;
import com.openclassrooms.safetynet.model.Person;

@Component
public class FireDTOMapper {

	public List<FireDTO> convertFireStationIntoFireDTO(FireStation fireStation) {
		List<FireDTO> listFireDTO = new ArrayList<>();
		String medications;
		String allergies;
		int age;
		FireDTO fireDTO;
		for (Person person : fireStation.getPersons()) {
			medications = "";
			allergies = "";
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
			age = person.getMedicalRecord().calculateAge(person.getMedicalRecord().getBirthDate());
			fireDTO = new FireDTO(person.getFirstName(), person.getLastName(), person.getPhone(), age, medications,
					allergies);
			listFireDTO.add(fireDTO);
		}
		return listFireDTO;

	}

}
