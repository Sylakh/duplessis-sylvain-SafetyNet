package com.openclassrooms.safetynet.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import com.openclassrooms.safetynet.DTO.PersonDTO;
import com.openclassrooms.safetynet.mapper.PersonMapper;
import com.openclassrooms.safetynet.model.FireStation;
import com.openclassrooms.safetynet.model.Person;
import com.openclassrooms.safetynet.repository.FireStationRepository;
import com.openclassrooms.safetynet.repository.PersonRepository;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class PersonServiceTest {

	@Mock
	private PersonRepository personRepository;

	@Mock
	private FireStationRepository fireStationRepository;

	@Mock
	private PersonMapper personMapper;

	@InjectMocks
	private PersonService personService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void savePersonSuccessTest() {
		// given
		PersonDTO personDTO = new PersonDTO("firstName", "lastName", "address", "city", "zip", "phone", "email");
		Person person = new Person();
		person.setFirstName("firstName");
		person.setLastName("lastName");
		person.setAddress("address");
		FireStation fireStation = new FireStation();
		fireStation.setAddress("address");

		when(personMapper.convertPersonFromPersonDTO(any(PersonDTO.class))).thenReturn(person);
		when(fireStationRepository.findByAddress(anyString())).thenReturn(Optional.of(fireStation));
		when(personRepository.save(any(Person.class))).thenReturn(person);
		when(personMapper.convertPersonToPersonDTO(any(Person.class))).thenReturn(personDTO);

		// when
		PersonDTO savedPersonDTO = personService.savePerson(personDTO);

		// then
		assertNotNull(savedPersonDTO);
		verify(personRepository).save(any(Person.class));
		verify(fireStationRepository).findByAddress(anyString());

	}

	@Test
	public void deletePersonByFirstNameAndLastNameSuccessTest() throws Exception {
		// given
		Person person = new Person();
		person.setId((long) 1);
		person.setFirstName("John");
		person.setLastName("Doe");
		person.setAddress("address");
		when(personRepository.findByFirstNameAndLastName(anyString(), anyString())).thenReturn(Optional.of(person));
		// when
		personService.deletePersonByFirstNameAndLastName("John", "Doe");
		// then
		verify(personRepository).deleteById(any(Long.class));
	}

	@Test
	public void deletePersonByFirstNameAndLastNameNotFoundTest() {
		// given
		when(personRepository.findByFirstNameAndLastName(anyString(), anyString())).thenReturn(Optional.empty());
		// when & Then
		assertThrows(Exception.class, () -> personService.deletePersonByFirstNameAndLastName("Nonexistent", "Person"));
	}

	@Test
	public void updatePersonByFirstNameAndLastNameSuccessTest() throws Exception {
		// given
		PersonDTO personDTO = new PersonDTO("John", "Doe", "address", "city", "zip", "phone", "email");
		Person person = new Person();
		person.setFirstName("John");
		person.setLastName("Doe");
		person.setAddress("address");
		FireStation fireStation = new FireStation();
		fireStation.setAddress("address");

		when(personMapper.convertPersonFromPersonDTO(any(PersonDTO.class))).thenReturn(person);
		when(personRepository.findByFirstNameAndLastName(anyString(), anyString())).thenReturn(Optional.of(person));
		when(personRepository.save(any(Person.class))).thenReturn(person);
		when(fireStationRepository.findByAddress(anyString())).thenReturn(Optional.of(fireStation));
		when(personMapper.convertPersonToPersonDTO(any(Person.class))).thenReturn(personDTO);
		// when
		PersonDTO updatedPersonDTO = personService.updatePersonByFirstNameAndLastName(personDTO);
		// Then
		assertNotNull(updatedPersonDTO);
		verify(personRepository).save(person);
	}

	@Test
	public void updatePersonByFirstNameAndLastNamePersonNotFoundTest() {
		// Given
		PersonDTO personDTO = new PersonDTO("Jane", "Doe", "124 Main St", "Springfield", "12346", "555-1235",
				"jane.doe@example.com");
		Person person = new Person();
		person.setFirstName("Jane");
		person.setLastName("Doe");

		when(personMapper.convertPersonFromPersonDTO(personDTO)).thenReturn(person);
		when(personRepository.findByFirstNameAndLastName("Jane", "Doe")).thenReturn(Optional.empty());

		// When & Then
		Exception exception = assertThrows(Exception.class, () -> {
			personService.updatePersonByFirstNameAndLastName(personDTO);
		});
		assertEquals("Person not found, new data not recorded", exception.getMessage());
		verify(personRepository, times(1)).findByFirstNameAndLastName("Jane", "Doe");
		verify(personRepository, never()).save(any(Person.class));
	}
}
