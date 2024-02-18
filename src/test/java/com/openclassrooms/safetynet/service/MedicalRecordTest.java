package com.openclassrooms.safetynet.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import com.openclassrooms.safetynet.DTO.MedicalRecordDTO;
import com.openclassrooms.safetynet.mapper.MedicalRecordMapper;
import com.openclassrooms.safetynet.model.Allergy;
import com.openclassrooms.safetynet.model.MedicalRecord;
import com.openclassrooms.safetynet.model.Medication;
import com.openclassrooms.safetynet.model.Person;
import com.openclassrooms.safetynet.repository.AllergyRepository;
import com.openclassrooms.safetynet.repository.MedicalRecordRepository;
import com.openclassrooms.safetynet.repository.MedicationRepository;
import com.openclassrooms.safetynet.repository.PersonRepository;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class MedicalRecordTest {

	@Mock
	private MedicalRecordRepository medicalRecordRepository;

	@Mock
	private MedicationRepository medicationRepository;

	@Mock
	private AllergyRepository allergyRepository;

	@Mock
	private MedicalRecordMapper medicalRecordMapper;

	@Mock
	private PersonRepository personRepository;

	@InjectMocks
	private MedicalRecordService medicalRecordService;

	// Example setup, adapt as needed
	@BeforeEach
	void setUp() {
		// Initialization code if needed
	}

	@Test
	void deletePMedicalRecordByName_thenDeleteMedicalRecordTest() throws Exception {
		// Given
		String firstName = "John";
		String lastName = "Doe";
		MedicalRecord medicalRecord = new MedicalRecord();
		when(medicalRecordRepository.findByFirstNameAndLastName(firstName, lastName))
				.thenReturn(Optional.of(medicalRecord));

		// When
		medicalRecordService.deleteMedicalRecord(firstName, lastName);

		// Then
		verify(medicalRecordRepository).deleteById(medicalRecord.getMedicalRecordId());
	}

	@Test
	void deleteMedicalRecordByName_andPatientDoesNotExist_thenThrowExceptionTest() {
		// Given
		String firstName = "Nonexistent";
		String lastName = "Person";
		when(medicalRecordRepository.findByFirstNameAndLastName(firstName, lastName)).thenReturn(Optional.empty());

		// When & Then
		Exception exception = assertThrows(Exception.class,
				() -> medicalRecordService.deleteMedicalRecord(firstName, lastName));
		assertEquals("Medicalrecord not found, no data deleted", exception.getMessage());
	}

	@Test
	void whenCreateMedicalRecord_thenSaveMedicalRecordTest() throws Exception {
		// Given
		MedicalRecordDTO medicalRecordDTO = new MedicalRecordDTO("John", "Doe", "birthDate", null, null);
		MedicalRecord medicalRecord = new MedicalRecord();
		medicalRecord.setFirstName("John");
		medicalRecord.setLastName("Doe");
		Person person = new Person();
		person.setFirstName("John");
		person.setLastName("Doe");
		person.setAddress("address");

		when(medicalRecordMapper.convertFromDTO(medicalRecordDTO)).thenReturn(medicalRecord);
		when(personRepository.findByFirstNameAndLastName(anyString(), anyString())).thenReturn(Optional.of(person));
		when(medicalRecordRepository.save(any(MedicalRecord.class))).thenReturn(medicalRecord);
		when(medicalRecordMapper.convertToDTO(any(MedicalRecord.class))).thenReturn(medicalRecordDTO);

		// When
		MedicalRecordDTO result = medicalRecordService.createMedicalRecord(medicalRecordDTO);

		// Then
		assertNotNull(result);
		verify(medicalRecordRepository).save(any(MedicalRecord.class));
	}

	@Test
	void whenCreateMedicalRecord_andPersonDoesNotExist_thenThrowExceptionTest() {
		// Given
		MedicalRecordDTO medicalRecordDTO = new MedicalRecordDTO("John", "Doe", "birthDate", null, null);
		MedicalRecord medicalRecord = new MedicalRecord();
		medicalRecord.setFirstName("John");
		medicalRecord.setLastName("Doe");

		when(medicalRecordMapper.convertFromDTO(medicalRecordDTO)).thenReturn(medicalRecord);
		when(personRepository.findByFirstNameAndLastName(anyString(), anyString())).thenReturn(Optional.empty());

		// When & Then
		Exception exception = assertThrows(Exception.class,
				() -> medicalRecordService.createMedicalRecord(medicalRecordDTO));
		assertEquals("Person not found, new data not recorded", exception.getMessage());
	}

	@Test
	void whenUpdateMedicalRecord_thenCorrectlyUpdatedTest() throws Exception {
		// Given
		MedicalRecordDTO medicalRecordDTO = new MedicalRecordDTO("firstName", "lastName", "birthDate", null, null);
		MedicalRecord medicalRecord = new MedicalRecord();
		medicalRecord.setFirstName("John");
		medicalRecord.setLastName("Doe");

		when(medicalRecordMapper.convertFromDTO(medicalRecordDTO)).thenReturn(medicalRecord);
		when(medicalRecordRepository.findByFirstNameAndLastName(anyString(), anyString()))
				.thenReturn(Optional.of(medicalRecord));
		when(medicalRecordRepository.save(any(MedicalRecord.class))).thenReturn(medicalRecord);
		when(medicalRecordMapper.convertToDTO(any(MedicalRecord.class))).thenReturn(medicalRecordDTO);
		// When
		MedicalRecordDTO result = medicalRecordService.updateMedicalRecord(medicalRecordDTO);
		// Then
		assertNotNull(result);
		assertEquals("firstName", result.firstName());
	}

	@Test
	void whenUpdateMedicalRecord_andRecordDoesNotExist_thenThrowExceptionTest() {
		// Given
		MedicalRecordDTO medicalRecordDTO = new MedicalRecordDTO("firstName", "lastName", "birthDate", null, null);
		MedicalRecord medicalRecord = new MedicalRecord();
		medicalRecord.setFirstName("John");
		medicalRecord.setLastName("Doe");

		when(medicalRecordMapper.convertFromDTO(medicalRecordDTO)).thenReturn(medicalRecord);
		when(medicalRecordRepository.findByFirstNameAndLastName(anyString(), anyString())).thenReturn(Optional.empty());
		// When & Then
		Exception exception = assertThrows(Exception.class,
				() -> medicalRecordService.updateMedicalRecord(medicalRecordDTO));
		assertEquals("MedicalRecord not found, new data not updated", exception.getMessage());
	}

	@Test
	void whenCreateMedicalRecord_thenSaveMedicationsAndAllergiesTest() throws Exception {
		// Given
		MedicalRecordDTO medicalRecordDTO = new MedicalRecordDTO("John", "Doe", "birthDate", new ArrayList<>(),
				new ArrayList<>());
		MedicalRecord medicalRecord = new MedicalRecord();
		medicalRecord.setFirstName("John");
		medicalRecord.setLastName("Doe");
		Person person = new Person();
		person.setFirstName("John");
		person.setLastName("Doe");

		List<Medication> medications = new ArrayList<>();
		List<Allergy> allergies = new ArrayList<>();
		Allergy allergy = new Allergy();
		allergy.setAllergyName("allergy");
		Medication medication = new Medication();
		medication.setMedication("medication");
		allergies.add(allergy);
		medications.add(medication);
		medicalRecord.setMedications(medications);
		medicalRecord.setAllergies(allergies);

		when(medicalRecordMapper.convertFromDTO(medicalRecordDTO)).thenReturn(medicalRecord);
		when(personRepository.findByFirstNameAndLastName("John", "Doe")).thenReturn(Optional.of(person));
		when(medicalRecordRepository.save(medicalRecord)).thenReturn(medicalRecord);
		when(medicalRecordMapper.convertToDTO(any(MedicalRecord.class))).thenReturn(medicalRecordDTO);

		// When
		MedicalRecordDTO result = medicalRecordService.createMedicalRecord(medicalRecordDTO);

		// Then
		assertNotNull(result);
		verify(medicationRepository, times(1)).save(any(Medication.class));
		verify(allergyRepository, times(1)).save(any(Allergy.class));
	}

	@Test
	void whenUpdateMedicalRecord_thenCorrectlyDeleteOldMedicationsAndAllergiesTest() throws Exception {
		// Given
		MedicalRecord medicalRecord = new MedicalRecord();
		medicalRecord.setFirstName("John");
		medicalRecord.setLastName("Doe");
		medicalRecord.setMedicalRecordId((long) 1);

		List<Medication> medications = new ArrayList<>();
		List<Allergy> allergies = new ArrayList<>();
		Allergy allergy = new Allergy();
		allergy.setAllergyName("allergy");
		allergy.setId((long) 1);
		Medication medication = new Medication();
		medication.setMedication("medication");
		medication.setId((long) 1);
		allergies.add(allergy);
		medications.add(medication);
		medicalRecord.setMedications(medications);
		medicalRecord.setAllergies(allergies);

		MedicalRecordDTO medicalRecordDTO = new MedicalRecordDTO("John", "Doe", "birthDate", null, null);
		medicalRecord.setMedications(medications);
		medicalRecord.setAllergies(allergies);

		when(medicalRecordMapper.convertFromDTO(medicalRecordDTO)).thenReturn(medicalRecord);
		when(allergyRepository.findAllByMedicalRecord(medicalRecord)).thenReturn(allergies);
		when(medicationRepository.findAllByMedicalRecord(medicalRecord)).thenReturn(medications);
		when(medicalRecordRepository.findByFirstNameAndLastName(anyString(), anyString()))
				.thenReturn(Optional.of(medicalRecord));
		when(medicalRecordRepository.save(any(MedicalRecord.class))).thenReturn(medicalRecord);
		when(medicalRecordMapper.convertToDTO(any(MedicalRecord.class))).thenReturn(medicalRecordDTO);

		// When
		MedicalRecordDTO result = medicalRecordService.updateMedicalRecord(medicalRecordDTO);

		// Then
		assertNotNull(result);
		verify(allergyRepository, times(1)).deleteById(anyLong());
		verify(medicationRepository, times(1)).deleteById(anyLong());
	}

}
