package com.openclassrooms.safetynet.service;

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

	public PersonDTO savePerson(PersonDTO personDTO) {
		Person person = personMapper.convertPersonFromPersonDTO(personDTO);
		if (person != null) {
			Person savedPerson = personRepository.save(person);
			return personMapper.convertPersonToPersonDTO(savedPerson);
		} else {
			return null;
		}

	}

	/**
	 * public List<PersonDTO> getAllPerson() { List<Person> listPerson =
	 * personRepository.findAll(); List<PersonDTO> listPersonDTO = new
	 * ArrayList<>(); for (Person person : listPerson) {
	 * listPersonDTO.add(personMapper.convertPersonToPersonDTO(person)); } return
	 * listPersonDTO; }
	 */
	/**
	 * public PersonDTO createPerson(PersonDTO personDTO) {
	 * 
	 * Person personToSave = personMapper.convertPersonFromPersonDTO(personDTO);
	 * Optional<FireStation> optionalFireStation =
	 * fireStationRepository.findByAddress(personDTO.address()); if
	 * (optionalFireStation.isPresent()) { logger.info("firestation found");
	 * FireStation fireStationFound = optionalFireStation.get();
	 * fireStationFound.addPerson(personToSave); return
	 * personMapper.convertPersonToPersonDTO(personRepository.save(personToSave)); }
	 * else { return null; } }
	 */
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

	public PersonDTO updatePersonByFirstNameAndLastName(PersonDTO personDTO) throws Exception {
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
			updatedPerson.setFireStation(person.getFireStation());
			updatedPerson.setMedicalRecord(person.getMedicalRecord());
			logger.info("update process done");
			return personMapper.convertPersonToPersonDTO(personRepository.save(updatedPerson));
		} else {
			logger.error("person not found");
			throw new Exception("Person not found, new data not recorded");
		}

	}
}
