package com.openclassrooms.safetynet.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.safetynet.DTO.PersonDTO;
import com.openclassrooms.safetynet.service.PersonService;

@RestController
@RequestMapping("/person")
public class PersonController {

	private static final Logger logger = LogManager.getLogger("PersonController");

	@Autowired
	private PersonService personService;

	@PostMapping
	public PersonDTO createPerson(@RequestBody PersonDTO personDTO) {
		logger.info("Create a new person in database");
		return personService.createPerson(personDTO);
	}

	@DeleteMapping
	public void deletePerson(@RequestParam("firstName") String firstName, @RequestParam("lastName") String lastName)
			throws Exception {
		logger.info("delete process by firstname and lastname begins");
		personService.deletePerson(firstName, lastName);
	}

	@PutMapping
	public PersonDTO updatePerson(@RequestBody PersonDTO personDTO) throws Exception {
		logger.info("update process begins");
		return personService.updatePerson(personDTO);

	}

}
