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
import com.openclassrooms.safetynet.model.FireStation;
import com.openclassrooms.safetynet.model.Person;
import com.openclassrooms.safetynet.repository.FireStationRepository;
import com.openclassrooms.safetynet.repository.PersonRepository;

import jakarta.transaction.Transactional;
import lombok.Data;

@Data
@Service
public class PersonService {

	private static final Logger logger = LogManager.getLogger("PersonService");

	@Autowired
	private PersonRepository personRepository;

	@Autowired
	private FireStationRepository fireStationRepository;

	@Autowired
	private PersonMapper personMapper;

	@Transactional
	public PersonDTO createPerson(PersonDTO personDTO) {
		Person person = personMapper.convertPersonFromPersonDTO(personDTO);

		if (person != null) {
			Optional<FireStation> optionalFireStation = fireStationRepository.findByAddress(person.getAddress());
			Person savedPerson = personRepository.save(person);
			if (optionalFireStation.isPresent()) {
				FireStation fireStation = optionalFireStation.get();
				List<Person> persons = new ArrayList<>();
				persons = personRepository.findAllByAddress(fireStation.getAddress());
				fireStation.setPersons(persons);
				persons.add(savedPerson);
				fireStationRepository.save(fireStation);
			}
			logger.info("creation of a new person done");
			return personMapper.convertPersonToPersonDTO(savedPerson);
		} else {
			logger.error("problem of conversion with body request");
			return null;
		}

	}

	@Transactional
	public void deletePerson(String firstName, String lastName) throws Exception {

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

	@Transactional
	public PersonDTO updatePerson(PersonDTO personDTO) throws Exception {
		Person person = personMapper.convertPersonFromPersonDTO(personDTO);
		Optional<Person> optionalPerson = personRepository.findByFirstNameAndLastName(person.getFirstName(),
				person.getLastName());
		if (optionalPerson.isPresent()) {
			Person updatedPerson = optionalPerson.get();
			updatedPerson.setAddress(person.getAddress());
			updatedPerson.setCity(person.getCity());
			updatedPerson.setZip(person.getZip());
			updatedPerson.setPhone(person.getPhone());
			updatedPerson.setEmail(person.getEmail());
			Optional<FireStation> optionalFireStation = fireStationRepository.findByAddress(updatedPerson.getAddress());
			updatedPerson.setMedicalRecord(person.getMedicalRecord());
			if (optionalFireStation.isPresent()) {
				FireStation fireStation = optionalFireStation.get();
				List<Person> persons = new ArrayList<>();
				persons = personRepository.findAllByAddress(fireStation.getAddress());
				persons.add(updatedPerson);
				fireStation.setPersons(persons);
				fireStationRepository.save(fireStation);
			} else {
				personRepository.updateFireStationReferenceNull(updatedPerson.getId());
			}
			logger.info("update process done");
			return personMapper.convertPersonToPersonDTO(personRepository.save(updatedPerson));
		} else {
			logger.error("person not found");
			throw new Exception("Person not found, new data not recorded");
		}

	}
}
