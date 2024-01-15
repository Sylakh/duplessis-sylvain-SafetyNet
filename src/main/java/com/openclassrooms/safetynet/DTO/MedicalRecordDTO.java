package com.openclassrooms.safetynet.DTO;

import java.util.List;

public record MedicalRecordDTO(String firstName, String lastName, String birthDate, List<String> medications,
		List<String> allergies) {

	public String firstName() {
		return firstName;
	}

	public String lastName() {
		return lastName;
	}

	public String birthDate() {
		return birthDate;
	}

	public List<String> medications() {
		return medications;
	}

	public List<String> allergies() {
		return allergies;
	}

}
