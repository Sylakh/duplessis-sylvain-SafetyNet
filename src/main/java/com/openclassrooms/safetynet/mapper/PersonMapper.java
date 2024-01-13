package com.openclassrooms.safetynet.mapper;

import org.springframework.stereotype.Component;

import com.openclassrooms.safetynet.DTO.PersonDTO;
import com.openclassrooms.safetynet.model.Person;

@Component
public class PersonMapper {

	public PersonDTO convertPersonToPersonDTO(Person person) {
		return new PersonDTO(person.getFirstName(), person.getLastName(), person.getAddress(), person.getCity(),
				person.getZip(), person.getPhone(), person.getEmail());
	}

}
