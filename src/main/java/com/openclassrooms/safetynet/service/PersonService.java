package com.openclassrooms.safetynet.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.openclassrooms.safetynet.DTO.PersonDTO;
import com.openclassrooms.safetynet.mapper.PersonMapper;
import com.openclassrooms.safetynet.model.Person;
import com.openclassrooms.safetynet.repository.PersonRepository;

import lombok.Data;

@Data
@Service
public class PersonService {

	private static final Logger logger = LogManager.getLogger("PersonService");

	@Autowired
	private PersonRepository personRepository;

	@Autowired
	private PersonMapper personMapper;

	public Person savePerson(Person person) {
		Person savedPerson = personRepository.save(person);
		return savedPerson;
	}

	public Optional<Person> findPersonByFirstNameAndLastName(String firstName, String lastName) {
		return personRepository.findByFirstNameAndLastName(firstName, lastName);
	}

	public void deleteById(Long id) {
		personRepository.deleteById(id);

	}

	public List<Person> getAllPersonByCity(String city) {
		return personRepository.findAllByCity(city);
	}

	public List<Person> getAllPersonByAddress(String address) {
		return personRepository.findAllByAddress(address);
	}

	public List<PersonDTO> getAllPerson() {
		List<Person> listPerson = personRepository.findAll();
		List<PersonDTO> listPersonDTO = new ArrayList<>();
		for (Person person : listPerson) {
			listPersonDTO.add(personMapper.convertPersonToPersonDTO(person));
		}
		return listPersonDTO;
	}

	public PersonDTO createPerson(Person person) {
		return personMapper.convertPersonToPersonDTO(personRepository.save(person));
	}

	public void deletePersonByFirstNameAndLastName(String firstName, String lastName) throws Exception {

		Optional<Person> optionalPerson = personRepository.findByFirstNameAndLastName(firstName, lastName);
		if (optionalPerson.isPresent()) {
			Person deletedPerson = optionalPerson.get();
			personRepository.deleteById(deletedPerson.getId());
			logger.info("delete process done");
		} else {
			logger.error("person not found");
			throw new Exception("Person not found by endpoint /person/firstname/lastname");
		}
	}

	public PersonDTO updatePersonByFirstNameAndLastName(Person person) throws Exception {
		Optional<Person> optionalPerson = personRepository.findByFirstNameAndLastName(person.getFirstName(),
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
			return personMapper.convertPersonToPersonDTO(personRepository.save(updatedPerson));
		} else {
			logger.error("person not found");
			throw new Exception("Person not found, new data not recorded");
		}

	}
}
