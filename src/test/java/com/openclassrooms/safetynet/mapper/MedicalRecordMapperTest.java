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
	public void testConvertToDTO() {
		// Préparation de l'objet MedicalRecord
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
		/**
		 * List<String> listMedication = new ArrayList<>();
		 * listMedication.add("medication1"); listMedication.add("medication2");
		 * List<String> listAllergy = new ArrayList<>(); listAllergy.add("allergy1");
		 * listAllergy.add("allergy2");
		 */
		MedicalRecord medicalRecord = mock(MedicalRecord.class);
		when(medicalRecord.getFirstName()).thenReturn("John");
		when(medicalRecord.getLastName()).thenReturn("Doe");
		when(medicalRecord.getBirthDate()).thenReturn("01/01/1990");
		when(medicalRecord.getMedication()).thenReturn(medications);
		when(medicalRecord.getAllergy()).thenReturn(allergies);

		// Exécution de la méthode à tester
		MedicalRecordDTO dto = medicalRecordMapper.convertToDTO(medicalRecord);

		// Vérification des résultats
		assertEquals("John", dto.firstName());
		assertEquals("Doe", dto.lastName());
		assertEquals("01/01/1990", dto.birthDate());
		assertEquals(Arrays.asList("medication1", "medication2"), dto.medications());
		assertEquals(Arrays.asList("allergy1", "allergy2"), dto.allergies());
	}

	@Test
	public void testConvertFromDTO() {
		// Préparation de l'objet MedicalRecordDTO
		List<String> medications = new ArrayList<>();
		medications.add("medication1");
		medications.add("medication2");
		List<String> allergies = new ArrayList<>();
		allergies.add("allergy1");
		allergies.add("allergy2");
		MedicalRecordDTO dto = new MedicalRecordDTO("Jane", "Doe", "02/02/1990", medications, allergies);

		// Exécution de la méthode à tester
		MedicalRecord medicalRecord = medicalRecordMapper.convertFromDTO(dto);

		// Vérification des résultats
		assertEquals("Jane", medicalRecord.getFirstName());
		assertEquals("Doe", medicalRecord.getLastName());
		assertEquals("Jane,Doe", medicalRecord.getName());
		assertEquals("02/02/1990", medicalRecord.getBirthDate());
		assertEquals(2, medicalRecord.getMedication().size());
		assertEquals("medication1", medicalRecord.getMedication().get(0).getMedication());
		assertEquals(2, medicalRecord.getAllergy().size());
		assertEquals("allergy1", medicalRecord.getAllergy().get(0).getAllergyName());
	}
}
