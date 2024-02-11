package com.openclassrooms.safetynet.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import com.openclassrooms.safetynet.DTO.FireStationDTO;
import com.openclassrooms.safetynet.mapper.FireStationMapper;
import com.openclassrooms.safetynet.model.FireStation;
import com.openclassrooms.safetynet.model.Person;
import com.openclassrooms.safetynet.repository.FireStationRepository;
import com.openclassrooms.safetynet.repository.PersonRepository;

@SpringBootTest
public class FireStationServiceTest {

	@Mock
	private FireStationRepository fireStationRepository;

	@Mock
	private PersonRepository personRepository;

	@Mock
	private FireStationMapper fireStationMapper;

	@InjectMocks
	private FireStationService fireStationService;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void createMappingAddressWithStationTest() {
		// Given
		FireStation fireStation = new FireStation();
		fireStation.setAddress("Test Address");
		List<Person> persons = new ArrayList<>();
		FireStationDTO fireStationDTO = new FireStationDTO("Test", "Rennes");

		when(personRepository.findAllByAddress(anyString())).thenReturn(persons);
		when(fireStationRepository.save(any(FireStation.class))).thenReturn(fireStation);
		when(fireStationMapper.convertFireStationToFireStationDTO(any(FireStation.class))).thenReturn(fireStationDTO);
		// When
		FireStationDTO result = fireStationService.createMappingAddressWithStation(fireStation);
		// Then
		assertNotNull(result);
		verify(personRepository).findAllByAddress("Test Address");
		verify(fireStationRepository).save(fireStation);
		verify(fireStationMapper).convertFireStationToFireStationDTO(fireStation);

	}

	@Test
	public void updateFireStationOfAnAddressTest() throws Exception {
		// Given
		FireStation fireStation = new FireStation();
		fireStation.setAddress("Test Address");
		fireStation.setStation("Test Station");
		Optional<FireStation> optionalFireStation = Optional.of(fireStation);
		FireStationDTO fireStationDTO = new FireStationDTO("test", "test");

		when(fireStationRepository.findByAddress(anyString())).thenReturn(optionalFireStation);
		when(fireStationRepository.save(any(FireStation.class))).thenReturn(fireStation);
		when(fireStationMapper.convertFireStationToFireStationDTO(any(FireStation.class))).thenReturn(fireStationDTO);
		// When
		FireStationDTO result = fireStationService.updateFireStationOfAnAddress(fireStation);
		// Then
		assertNotNull(result);
		verify(fireStationRepository).findByAddress("Test Address");
		verify(fireStationRepository).save(fireStation);
		verify(fireStationMapper).convertFireStationToFireStationDTO(fireStation);
	}

	@Test
	public void deleteAnAddressOrAStationByAddressTest() throws Exception {
		// Given
		FireStation fireStation = new FireStation();
		fireStation.setAddress("Test Address");
		Optional<FireStation> optionalFireStation = Optional.of(fireStation);

		when(fireStationRepository.findByAddress(anyString())).thenReturn(optionalFireStation);
		doNothing().when(fireStationRepository).deleteByAddress(anyString());
		// When
		fireStationService.deleteAnAddressOrAStation(fireStation);
		// Then
		verify(fireStationRepository).findByAddress("Test Address");
		verify(fireStationRepository).deleteByAddress("Test Address");
	}

	@Test
	public void deleteAnAddressOrAStationByStationTest() throws Exception {
		// Given
		FireStation fireStation = new FireStation();
		fireStation.setStation("Test Station");
		List<FireStation> fireStations = new ArrayList<>();
		fireStations.add(fireStation);

		when(fireStationRepository.findByStation(anyString())).thenReturn(fireStations);
		doNothing().when(fireStationRepository).deleteByAddress(anyString());
		// When
		fireStationService.deleteAnAddressOrAStation(fireStation);
		// Then
		verify(fireStationRepository).findByStation("Test Station");
	}

	@Test
	public void createMappingAddressWithStationWithNonEmptyPersonsTest() {
		// Given
		FireStation fireStation = new FireStation();
		fireStation.setAddress("Test Address");
		List<Person> persons = Arrays.asList(new Person()); // Simuler une liste de personnes non vide
		FireStationDTO fireStationDTO = new FireStationDTO("Test", "Rennes");

		when(personRepository.findAllByAddress("Test Address")).thenReturn(persons);
		when(fireStationRepository.save(fireStation)).thenReturn(fireStation);
		when(fireStationMapper.convertFireStationToFireStationDTO(fireStation)).thenReturn(fireStationDTO);
		// When
		FireStationDTO result = fireStationService.createMappingAddressWithStation(fireStation);
		// Then
		assertNotNull(result);
		verify(personRepository).findAllByAddress("Test Address");
		verify(fireStationRepository).save(fireStation);
		verify(fireStationMapper).convertFireStationToFireStationDTO(fireStation);
		assertEquals(persons, fireStation.getPersons()); // S'assurer que la liste des personnes est bien associée à la
															// station
	}

	@Test
	public void updateFireStationOfAnAddressNotFoundTest() {
		// Given
		FireStation fireStation = new FireStation();
		fireStation.setAddress("Unknown Address");
		when(fireStationRepository.findByAddress("Unknown Address")).thenReturn(Optional.empty());

		// When & Then
		assertThrows(Exception.class, () -> {
			fireStationService.updateFireStationOfAnAddress(fireStation);
		});
	}

	@Test
	public void deleteAnAddressOrAStationNotFoundTest() {
		// Given
		FireStation fireStation = new FireStation(); // Aucune adresse ni station définie
		// When & Then
		assertThrows(Exception.class, () -> {
			fireStationService.deleteAnAddressOrAStation(fireStation);
		});
	}
}
