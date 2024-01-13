package com.openclassrooms.safetynet.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.safetynet.DTO.PersonDTO;
import com.openclassrooms.safetynet.mapper.PersonMapper;
import com.openclassrooms.safetynet.model.Person;
import com.openclassrooms.safetynet.service.PersonService;

@RestController
public class PersonController {

	private static final Logger logger = LogManager.getLogger("PersonController");

	@Autowired
	private PersonService personService;

	@Autowired
	private PersonMapper personMapper;

	/**
	 * Read - Get all persons
	 * 
	 * @return - An Iterable object of Person full filled
	 */
	@GetMapping("/person")
	public List<PersonDTO> getAllPerson() {
		logger.info("Get all persons from database");
		List<Person> listPerson = personService.getAllPerson();
		List<PersonDTO> listPersonDTO = new ArrayList<>();
		for (Person person : listPerson) {
			listPersonDTO.add(personMapper.convertPersonToPersonDTO(person));
		}
		return listPersonDTO;
	}

	/**
	 * Create - Add a new person
	 * 
	 * @param person An object person
	 * @return The person object saved
	 */
	@PostMapping("/person")
	public PersonDTO createPerson(@RequestBody Person person) {
		logger.info("Create a new person in database");
		return personMapper.convertPersonToPersonDTO(personService.savePerson(person));
	}

	/**
	 * Delete - Delete a person
	 * 
	 * @param lastName and firstName of an object person
	 * @throws Exception
	 */
	@DeleteMapping("/person/{firstName}/{lastName}")
	public void deletePersonByFirstNameAndLastName(@PathVariable("firstName") String firstName,
			@PathVariable("lastName") String lastName) throws Exception {
		logger.info("delete process by firstname and lastname begins");
		Optional<Person> optionalPerson = personService.findPersonByFirstNameAndLastName(firstName, lastName);
		if (optionalPerson.isPresent()) {
			Person deletedPerson = optionalPerson.get();
			personService.deleteById(deletedPerson.getId());
			logger.info("delete process done");
		} else {
			logger.error("person not found");
			throw new Exception("Person not found by endpoint /person/firstname/lastname");
		}
	}

	/**
	 * Delete - Delete a person
	 * 
	 * @param person object from request body
	 * @throws Exception
	 */
	@DeleteMapping("/person")
	public void deletePersonByRequestBody(@RequestBody Person person) throws Exception {
		logger.info("delete process by request body begins");
		Optional<Person> optionalPerson = personService.findPersonByFirstNameAndLastName(person.getFirstName(),
				person.getLastName());
		if (optionalPerson.isPresent()) {
			Person deletedPerson = optionalPerson.get();
			personService.deleteById(deletedPerson.getId());
			logger.info("delete process done");
		} else {
			logger.error("person not found");
			throw new Exception("Person not found by endpoint /person +body");
		}

	}

	/**
	 * Put - Update a person except firstname and lastname
	 * 
	 * @param person object from request body
	 * @throws Exception
	 */
	@PutMapping("/person")
	public PersonDTO updatePersonByFirstNameAndLastName(@RequestBody Person person) throws Exception {
		logger.info("update process begins");
		Optional<Person> optionalPerson = personService.findPersonByFirstNameAndLastName(person.getFirstName(),
				person.getLastName());
		if (optionalPerson.isPresent()) {
			Person updatedPerson = optionalPerson.get();
			if (person.getAddress() != null) {
				updatedPerson.setAddress(person.getAddress());
			}
			if (person.getCity() != null) {
				updatedPerson.setCity(person.getCity());
			}
			if (person.getZip() != null) {
				updatedPerson.setZip(person.getZip());
			}
			if (person.getPhone() != null) {
				updatedPerson.setPhone(person.getPhone());
			}
			if (person.getEmail() != null) {
				updatedPerson.setEmail(person.getEmail());
			}
			logger.info("update process done");
			return personMapper.convertPersonToPersonDTO(personService.savePerson(updatedPerson));
		} else {
			logger.error("person not found");
			throw new Exception("Person not found, new data not recorded");
		}

	}

}
