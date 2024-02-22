package com.openclassrooms.safetynet.DTO;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record CommonDTO(String firstName, String lastName, String phone, String address, String city, Integer age,
		String medication, String allergy) {

}
