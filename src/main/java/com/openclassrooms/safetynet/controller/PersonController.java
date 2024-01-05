package com.openclassrooms.safetynet.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.safetynet.model.Person;
import com.openclassrooms.safetynet.service.PersonService;

@RestController
public class PersonController {

	@Autowired
	private PersonService personService;

	/**
	 * Read - Get all persons
	 * 
	 * @return - An Iterable object of Person full filled
	 */
	@GetMapping("/person")
	public Iterable<Person> getAllPerson() {
		return personService.getAllPerson();
	}

	/**
	 * Create - Add a new person
	 * 
	 * @param person An object person
	 * @return The person object saved
	 */
	@PostMapping("/person")
	public Person createPerson(@RequestBody Person person) {
		return personService.savePerson(person);
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
		Optional<Person> optionalPerson = personService.findPersonByFirstNameAndLastName(firstName, lastName);
		if (optionalPerson.isPresent()) {
			Person deletedPerson = optionalPerson.get();
			personService.deleteById(deletedPerson.getId());
		} else {
			throw new Exception("Person not found by endpoint /person/firstname/lastname");
		}
	}

	@DeleteMapping("/person")
	public void deletePersonByRequestBody(@RequestBody Person person) throws Exception {

		Optional<Person> optionalPerson = personService.findPersonByFirstNameAndLastName(person.getFirstName(),
				person.getLastName());
		if (optionalPerson.isPresent()) {
			Person deletedPerson = optionalPerson.get();
			personService.deleteById(deletedPerson.getId());
		} else {
			throw new Exception("Person not found by endpoint /person +body");
		}

	}

	@PutMapping("/person")
	public Person updatePersonByFirstNameAndLastName(@RequestBody Person person) throws Exception {
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
			return personService.savePerson(updatedPerson);
		} else {
			throw new Exception("Person not found, new data not recorded");
		}

	}

}
