package com.openclassrooms.safetynet.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.openclassrooms.safetynet.model.Person;
import com.openclassrooms.safetynet.repository.PersonRepository;

import jakarta.transaction.Transactional;
import lombok.Data;

@Data
@Service
public class PersonService {

	@Autowired
	private PersonRepository personRepository;

	public Iterable<Person> getAllPerson() {
		return personRepository.findAll();
	}

	public Person savePerson(Person person) {
		Person savedPerson = personRepository.save(person);
		return savedPerson;
	}

	@Transactional
	public void deletePersonByFirstNameAndLastName(String firstName, String lastName) {
		personRepository.deleteByFirstNameAndLastName(firstName, lastName);
	}

	public Optional<Person> findPersonByFirstNameAndLastName(String firstName, String lastName) {
		return personRepository.findByFirstNameAndLastName(firstName, lastName);
	}

	public void deleteById(Long id) {
		personRepository.deleteById(id);

	}
}
