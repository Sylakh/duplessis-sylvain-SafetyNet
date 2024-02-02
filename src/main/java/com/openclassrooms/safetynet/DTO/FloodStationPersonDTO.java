package com.openclassrooms.safetynet.DTO;

import java.util.List;

import com.openclassrooms.safetynet.model.Allergy;
import com.openclassrooms.safetynet.model.Medication;

public record FloodStationPersonDTO(String firstName, String lastName, String phone, int age,
		List<Medication> medications, List<Allergy> allergies) {

}
