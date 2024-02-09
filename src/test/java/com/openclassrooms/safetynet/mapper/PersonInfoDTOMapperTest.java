package com.openclassrooms.safetynet.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.openclassrooms.safetynet.DTO.PersonInfoDTO;
import com.openclassrooms.safetynet.model.Allergy;
import com.openclassrooms.safetynet.model.MedicalRecord;
import com.openclassrooms.safetynet.model.Medication;
import com.openclassrooms.safetynet.model.Person;

public class PersonInfoDTOMapperTest {

	private PersonInfoDTOMapper personInfoDTOMapper;

	@BeforeEach
	public void setUp() {
		personInfoDTOMapper = new PersonInfoDTOMapper();
	}

	@Test
	public void testConvertPersonToPersonInfoDTO() {
		// Mock des objets nécessaires
		Person person = mock(Person.class);
		MedicalRecord medicalRecord = mock(MedicalRecord.class);
		List<Medication> medications = new ArrayList<>();
		Medication medication1 = new Medication();
		medication1.setMedication("medication1");
		medications.add(medication1);
		Medication medication2 = new Medication();
		medication2.setMedication("medication2");
		medications.add(medication2);
		List<Allergy> allergies = new ArrayList<>();
		Allergy allergy1 = new Allergy();
		allergy1.setAllergyName("allergy1");
		allergies.add(allergy1);
		Allergy allergy2 = new Allergy();
		allergy2.setAllergyName("allergy2");
		allergies.add(allergy2);

		// Configuration du comportement des mocks
		when(person.getFirstName()).thenReturn("Jane");
		when(person.getLastName()).thenReturn("Doe");
		when(person.getAddress()).thenReturn("address");
		when(person.getCity()).thenReturn("city");
		when(person.getMedicalRecord()).thenReturn(medicalRecord);
		when(medicalRecord.getMedication()).thenReturn(medications);
		when(medicalRecord.getAllergy()).thenReturn(allergies);
		when(person.getMedicalRecord().getBirthDate()).thenReturn("birthdate");
		when(medicalRecord.calculateAge(anyString())).thenReturn(40);

		// Appel de la méthode à tester
		PersonInfoDTO personInfoDTO = personInfoDTOMapper.convertPersonToPersonInfoDTO(person);

		// Vérifications des résultats
		assertEquals("Jane", personInfoDTO.firstName());
		assertEquals("Doe", personInfoDTO.lastName());
		assertEquals("address", personInfoDTO.address());
		assertEquals("city", personInfoDTO.city());
		assertEquals(40, personInfoDTO.age());
		assertEquals("medication1, medication2", personInfoDTO.medications());
		assertEquals("allergy1, allergy2", personInfoDTO.allergies());
	}

}
