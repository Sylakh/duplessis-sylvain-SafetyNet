package com.openclassrooms.safetynet.DTO;

import java.util.List;

public record MedicalRecordDTO(String firstName, String lastName, String birthDate, List<String> medications,
		List<String> allergies) {

}
