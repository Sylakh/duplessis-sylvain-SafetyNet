package com.openclassrooms.safetynet.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.openclassrooms.safetynet.DTO.CommonDTO;
import com.openclassrooms.safetynet.model.Allergy;
import com.openclassrooms.safetynet.model.FireStation;
import com.openclassrooms.safetynet.model.MedicalRecord;
import com.openclassrooms.safetynet.model.Medication;
import com.openclassrooms.safetynet.model.Person;

public class CommonDTOMapperTest {

	private CommonDTOMapper fireDTOMapper;

	@BeforeEach
	public void setUp() {
		fireDTOMapper = new CommonDTOMapper();
	}

	@Test
	public void convertFireStationIntoFireDTOTest() {
		// Given
		FireStation fireStation = mock(FireStation.class);
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

		when(fireStation.getPersons()).thenReturn(Arrays.asList(person));
		when(person.getFirstName()).thenReturn("John");
		when(person.getLastName()).thenReturn("Doe");
		when(person.getPhone()).thenReturn("123456789");
		when(person.getMedicalRecord()).thenReturn(medicalRecord);
		when(medicalRecord.getMedications()).thenReturn(medications);
		when(medicalRecord.getAllergies()).thenReturn(allergies);
		when(person.getMedicalRecord().getBirthDate()).thenReturn("birthdate");
		when(person.getMedicalRecord().calculateAge(anyString())).thenReturn(30);

		// When
		List<CommonDTO> result = fireDTOMapper.convertFireStationIntoFireDTO(fireStation);

		// Then
		assertEquals(1, result.size());
		CommonDTO fireDTO = result.get(0);
		assertEquals("John", fireDTO.firstName());
		assertEquals("Doe", fireDTO.lastName());
		assertEquals("123456789", fireDTO.phone());
		assertEquals(30, fireDTO.age());
		assertEquals("medication1, medication2", fireDTO.medication());
		assertEquals("allergy1, allergy2", fireDTO.allergy());
	}

	@Test
	public void convertPersonToFireDTOTest() {
		// Given
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

		when(person.getFirstName()).thenReturn("Jane");
		when(person.getLastName()).thenReturn("Doe");
		when(person.getAddress()).thenReturn("address");
		when(person.getCity()).thenReturn("city");
		when(person.getMedicalRecord()).thenReturn(medicalRecord);
		when(medicalRecord.getMedications()).thenReturn(medications);
		when(medicalRecord.getAllergies()).thenReturn(allergies);
		when(person.getMedicalRecord().getBirthDate()).thenReturn("birthdate");
		when(medicalRecord.calculateAge(anyString())).thenReturn(40);

		// When
		CommonDTO personInfoDTO = fireDTOMapper.convertPersonToPersonInfoDTO(person);

		// Then
		assertEquals("Jane", personInfoDTO.firstName());
		assertEquals("Doe", personInfoDTO.lastName());
		assertEquals("address", personInfoDTO.address());
		assertEquals("city", personInfoDTO.city());
		assertEquals(40, personInfoDTO.age());
		assertEquals("medication1, medication2", personInfoDTO.medication());
		assertEquals("allergy1, allergy2", personInfoDTO.allergy());
	}

}
