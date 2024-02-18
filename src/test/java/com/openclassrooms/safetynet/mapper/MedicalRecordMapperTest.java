package com.openclassrooms.safetynet.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.openclassrooms.safetynet.DTO.MedicalRecordDTO;
import com.openclassrooms.safetynet.model.Allergy;
import com.openclassrooms.safetynet.model.MedicalRecord;
import com.openclassrooms.safetynet.model.Medication;

public class MedicalRecordMapperTest {

	private MedicalRecordMapper medicalRecordMapper;

	@BeforeEach
	public void setUp() {
		medicalRecordMapper = new MedicalRecordMapper();
	}

	@Test
	public void convertMedicalRecordToDTOTest() {
		// Given
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

		MedicalRecord medicalRecord = mock(MedicalRecord.class);
		when(medicalRecord.getFirstName()).thenReturn("John");
		when(medicalRecord.getLastName()).thenReturn("Doe");
		when(medicalRecord.getBirthDate()).thenReturn("01/01/1990");
		when(medicalRecord.getMedications()).thenReturn(medications);
		when(medicalRecord.getAllergies()).thenReturn(allergies);

		// When
		MedicalRecordDTO dto = medicalRecordMapper.convertToDTO(medicalRecord);

		// Then
		assertEquals("John", dto.firstName());
		assertEquals("Doe", dto.lastName());
		assertEquals("01/01/1990", dto.birthDate());
		assertEquals(Arrays.asList("medication1", "medication2"), dto.medications());
		assertEquals(Arrays.asList("allergy1", "allergy2"), dto.allergies());
	}

	@Test
	public void convertMedicalRecordFromDTOTest() {
		// Given
		List<String> medications = new ArrayList<>();
		medications.add("medication1");
		medications.add("medication2");
		List<String> allergies = new ArrayList<>();
		allergies.add("allergy1");
		allergies.add("allergy2");
		MedicalRecordDTO dto = new MedicalRecordDTO("Jane", "Doe", "02/02/1990", medications, allergies);

		// When
		MedicalRecord medicalRecord = medicalRecordMapper.convertFromDTO(dto);

		// Then
		assertEquals("Jane", medicalRecord.getFirstName());
		assertEquals("Doe", medicalRecord.getLastName());
		assertEquals("Jane,Doe", medicalRecord.getName());
		assertEquals("02/02/1990", medicalRecord.getBirthDate());
		assertEquals(2, medicalRecord.getMedications().size());
		assertEquals("medication1", medicalRecord.getMedications().get(0).getMedication());
		assertEquals(2, medicalRecord.getAllergies().size());
		assertEquals("allergy1", medicalRecord.getAllergies().get(0).getAllergyName());
	}
}
