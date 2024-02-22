package com.openclassrooms.safetynet.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.openclassrooms.safetynet.DTO.CommonDTO;
import com.openclassrooms.safetynet.model.Allergy;
import com.openclassrooms.safetynet.model.FireStation;
import com.openclassrooms.safetynet.model.MedicalRecord;
import com.openclassrooms.safetynet.model.Medication;
import com.openclassrooms.safetynet.model.Person;

@Component
public class CommonDTOMapper {

	public List<CommonDTO> convertFireStationIntoFireDTO(FireStation fireStation) {
		List<CommonDTO> listFireDTO = new ArrayList<>();
		String medications;
		String allergies;
		int age;
		CommonDTO fireDTO;
		for (Person person : fireStation.getPersons()) {
			medications = "";
			allergies = "";
			for (Medication medication : person.getMedicalRecord().getMedications()) {
				if (medications == "") {
					medications = medication.getMedication();
				} else {
					medications = medications + ", " + medication.getMedication();
				}
			}
			for (Allergy allergy : person.getMedicalRecord().getAllergies()) {
				if (allergies == "") {
					allergies = allergy.getAllergyName();
				} else {
					allergies = allergies + ", " + allergy.getAllergyName();
				}
			}
			age = person.getMedicalRecord().calculateAge(person.getMedicalRecord().getBirthDate());
			fireDTO = new CommonDTO(person.getFirstName(), person.getLastName(), person.getPhone(), null, null, age,
					medications, allergies);
			listFireDTO.add(fireDTO);
		}
		return listFireDTO;

	}

	public CommonDTO convertPersonToPersonInfoDTO(Person person) {

		String medications = "";
		String allergies = "";
		for (Medication medication : person.getMedicalRecord().getMedications()) {
			if (medications == "") {
				medications = medication.getMedication();
			} else {
				medications = medications + ", " + medication.getMedication();
			}
		}
		for (Allergy allergy : person.getMedicalRecord().getAllergies()) {
			if (allergies == "") {
				allergies = allergy.getAllergyName();
			} else {
				allergies = allergies + ", " + allergy.getAllergyName();
			}
		}

		int age = person.getMedicalRecord().calculateAge(person.getMedicalRecord().getBirthDate());
		return new CommonDTO(person.getFirstName(), person.getLastName(), null, person.getAddress(), person.getCity(),
				age, medications, allergies);
	}

	public CommonDTO convertPatientToChildAlertDTO(MedicalRecord medicalRecord) {
		return new CommonDTO(medicalRecord.getFirstName(), medicalRecord.getLastName(), null, null, null,
				medicalRecord.calculateAge(medicalRecord.getBirthDate()), null, null);
	}

}
