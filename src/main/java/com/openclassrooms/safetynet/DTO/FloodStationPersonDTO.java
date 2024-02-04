package com.openclassrooms.safetynet.DTO;

import java.util.List;

public record FloodStationPersonDTO(String firstName, String lastName, String phone, int age, List<String> medications,
		List<String> allergies) {

}
