package com.openclassrooms.safetynet.mapper;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import com.openclassrooms.safetynet.DTO.PersonDTO;
import com.openclassrooms.safetynet.model.Person;

@Component
public class PersonMapper {

	private static final Logger logger = LogManager.getLogger("PersonMapper");

	public PersonDTO convertPersonToPersonDTO(Person person) {
		return new PersonDTO(person.getFirstName(), person.getLastName(), person.getAddress(), person.getCity(),
				person.getZip(), person.getPhone(), person.getEmail());
	}

	public Person convertPersonFromPersonDTO(PersonDTO personDTO) {
		logger.info("debut conversion person from DTO");

		Person person = new Person();
		person.setFirstName(personDTO.firstName());
		person.setLastName(personDTO.lastName());
		person.setAddress(personDTO.address());
		person.setCity(personDTO.city());
		person.setZip(personDTO.zip());
		person.setPhone(personDTO.phone());
		person.setEmail(personDTO.email());
		person.setName(personDTO.firstName() + "," + personDTO.lastName());

		return person;

	}

}
