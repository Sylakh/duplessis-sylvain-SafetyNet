package com.openclassrooms.safetynet.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import com.openclassrooms.safetynet.DTO.ChildAlertResponsDTO;
import com.openclassrooms.safetynet.DTO.CommonDTO;
import com.openclassrooms.safetynet.DTO.FireResponsDTO;
import com.openclassrooms.safetynet.DTO.FloodStationPersonDTO;
import com.openclassrooms.safetynet.DTO.PersonInfoResponsDTO;
import com.openclassrooms.safetynet.mapper.CommonDTOMapper;
import com.openclassrooms.safetynet.model.Allergy;
import com.openclassrooms.safetynet.model.FireStation;
import com.openclassrooms.safetynet.model.MedicalRecord;
import com.openclassrooms.safetynet.model.Medication;
import com.openclassrooms.safetynet.model.Person;
import com.openclassrooms.safetynet.repository.FireStationRepository;
import com.openclassrooms.safetynet.repository.MedicalRecordRepository;
import com.openclassrooms.safetynet.repository.PersonRepository;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class URLServiceTest {

	@Mock
	private PersonRepository personRepository;

	@Mock
	private FireStationRepository fireStationRepository;

	@Mock
	private MedicalRecordRepository medicalRecordRepository;

	@Mock
	private CommonDTOMapper fireDTOMapper;

	@InjectMocks
	private URLService urlService;

	@Test
	void communityEmailSuccessTest() {
		// Given
		String city = "city";
		Person person = new Person();
		person.setFirstName("John");
		person.setLastName("Doe");
		person.setAddress("address");
		person.setEmail("email");
		List<Person> persons = new ArrayList<>();
		persons.add(person);
		when(personRepository.findAllByCity(city)).thenReturn(persons);

		// When
		List<String> emails = urlService.communityEmail(city);

		// Then
		assertFalse(emails.isEmpty());
		assertEquals("email", emails.get(0));
		verify(personRepository).findAllByCity(city);
	}

	@Test
	void phoneAlertSuccessTest() {
		// Given
		String station = "station";
		FireStation fireStation = new FireStation();
		fireStation.setAddress("address");
		fireStation.setStation(station);
		List<FireStation> fireStations = new ArrayList<>();
		fireStations.add(fireStation);
		Person person = new Person();
		person.setFirstName("John");
		person.setLastName("Doe");
		person.setAddress("address");
		person.setPhone("phone");
		List<Person> persons = new ArrayList<>();
		persons.add(person);
		when(fireStationRepository.findByStation(station)).thenReturn(fireStations);
		when(personRepository.findAllByAddress(anyString())).thenReturn(persons);

		// When
		List<String> phones = urlService.phoneAlert(station);

		// Then
		assertFalse(phones.isEmpty());
		assertEquals("phone", phones.get(0));
		verify(fireStationRepository).findByStation(station);
	}

	@Test
	void childAlertSuccesTest() {
		// Given
		String address = "address";
		List<Person> personsAtAddress = new ArrayList<>();
		Person child = new Person();
		child.setFirstName("John");
		child.setLastName("Doe");
		child.setAddress("address");
		Person adult = new Person();
		adult.setFirstName("Jane");
		adult.setLastName("Doe");
		adult.setAddress("address");

		personsAtAddress.add(child);
		personsAtAddress.add(adult);

		MedicalRecord childMedicalRecord = new MedicalRecord();
		childMedicalRecord.setFirstName("John");
		childMedicalRecord.setLastName("Doe");
		childMedicalRecord.setBirthDate("01/01/2012");
		MedicalRecord adultMedicalRecord = new MedicalRecord();
		adultMedicalRecord.setFirstName("Jane");
		adultMedicalRecord.setLastName("Doe");
		adultMedicalRecord.setBirthDate("01/01/1990");

		when(personRepository.findAllByAddress(address)).thenReturn(personsAtAddress);
		when(medicalRecordRepository.findByFirstNameAndLastName("John", "Doe"))
				.thenReturn(Optional.of(childMedicalRecord));
		when(medicalRecordRepository.findByFirstNameAndLastName("Jane", "Doe"))
				.thenReturn(Optional.of(adultMedicalRecord));
		when(fireDTOMapper.convertPatientToChildAlertDTO(any(MedicalRecord.class))).thenAnswer(invocation -> {
			MedicalRecord record = invocation.getArgument(0);
			return new CommonDTO(record.getFirstName(), record.getLastName(), null, null, null,
					record.calculateAge(record.getBirthDate()), null, null);
		});

		// When
		ChildAlertResponsDTO response = urlService.childAlert(address);

		// Then
		assertNotNull(response);
		assertFalse(response.listChild().isEmpty());
		assertFalse(response.listAdult().isEmpty());
		assertEquals(1, response.listChild().size());
		assertEquals(1, response.listAdult().size());
		assertEquals("John", response.listChild().get(0).firstName());
		assertEquals("Jane", response.listAdult().get(0).firstName());
		verify(personRepository).findAllByAddress(address);
		verify(medicalRecordRepository, times(personsAtAddress.size())).findByFirstNameAndLastName(anyString(),
				anyString());
		verify(fireDTOMapper, times(personsAtAddress.size())).convertPatientToChildAlertDTO(any(MedicalRecord.class));
	}

	@Test
	void fireSuccesTest() {
		// Given
		String address = "123 Maple St";
		Optional<FireStation> optionalFireStation = Optional.of(new FireStation());
		optionalFireStation.get().setAddress(address);
		optionalFireStation.get().setStation("1");

		List<Person> personsAtAddress = new ArrayList<>();
		Person person1 = new Person();
		person1.setFirstName("John");
		person1.setLastName("Doe");
		person1.setAddress(address);
		person1.setPhone("111-222-3333");
		personsAtAddress.add(person1);

		MedicalRecord medicalRecord1 = new MedicalRecord();
		medicalRecord1.setFirstName("John");
		medicalRecord1.setLastName("Doe");
		medicalRecord1.setBirthDate("01/01/1990");

		List<CommonDTO> fireDTOs = new ArrayList<>();
		CommonDTO fireDTO = new CommonDTO("John", "Doe", "phone", null, null, 30, null, null);
		fireDTOs.add(fireDTO);

		when(fireStationRepository.findByAddress(address)).thenReturn(optionalFireStation);
		when(fireDTOMapper.convertFireStationIntoFireDTO(any(FireStation.class))).thenReturn(fireDTOs);

		// When
		FireResponsDTO response = urlService.fire(address);

		// Then
		assertNotNull(response);
		assertFalse(response.persons().isEmpty());
		assertEquals("l'adresse 123 Maple St est couverte par la caserne 1 .", response.fireStation());
		assertEquals(1, response.persons().size());
		assertEquals("John", response.persons().get(0).firstName());

		verify(fireStationRepository).findByAddress(address);
		verify(fireDTOMapper).convertFireStationIntoFireDTO(any(FireStation.class));
	}

	@Test
	void personInfoSuccesTest() {
		// Given
		String firstName = "John", lastName = "Doe";
		List<Person> persons = new ArrayList<>();
		Person person = new Person();
		person.setFirstName("John");
		person.setLastName("Doe");
		person.setEmail("email");
		persons.add(person);
		when(personRepository.findAllByLastName(lastName)).thenReturn(persons);
		when(personRepository.findByFirstNameAndLastName(firstName, lastName)).thenReturn(Optional.of(person));

		// When
		PersonInfoResponsDTO result = urlService.personInfo(firstName, lastName);

		// Then
		assertNotNull(result);
		verify(personRepository).findByFirstNameAndLastName(firstName, lastName);
	}

	@Test
	void floodStationsSuccesTest() {
		// Given
		String[] stations = { "1", "2" };
		List<FireStation> allStations = new ArrayList<>();
		FireStation fireStation2 = new FireStation();
		FireStation fireStation1 = new FireStation();
		fireStation1.setAddress("address 1");
		fireStation1.setStation("station 1");
		fireStation2.setAddress("address 2");
		fireStation2.setStation("station 2");
		allStations.add(fireStation1);
		allStations.add(fireStation2);

		Medication medication = new Medication();
		Allergy allergy = new Allergy();
		MedicalRecord medicalRecord = new MedicalRecord();
		List<Medication> medications = new ArrayList<>();
		medications.add(medication);
		List<Allergy> allergies = new ArrayList<>();
		allergies.add(allergy);
		medicalRecord.setMedications(medications);
		medicalRecord.setAllergies(allergies);
		medicalRecord.setFirstName("John");
		medicalRecord.setLastName("Doe");
		medicalRecord.setBirthDate("01/01/1990");
		Person person1 = new Person();
		person1.setFirstName("John");
		person1.setLastName("Doe");
		person1.setAddress("address 1");
		person1.setPhone("phone 1");
		person1.setMedicalRecord(medicalRecord);
		Person person2 = new Person();
		person2.setFirstName("Jane");
		person2.setLastName("Doe");
		person2.setAddress("address 2");
		person2.setPhone("phone 2");
		medicalRecord.setFirstName("Jane");
		medicalRecord.setBirthDate("01/01/1990");
		person2.setMedicalRecord(medicalRecord);

		when(fireStationRepository.findAllByStation(anyString())).thenReturn(allStations);

		// When
		java.util.Map<String, List<FloodStationPersonDTO>> response = urlService.floodStations(stations);

		// Then
		assertNotNull(response);
		assertEquals(2, response.size());
		verify(fireStationRepository, times(stations.length)).findAllByStation(anyString());
	}

	@Test
	void fireStationURLSuccesTest() {

		// Given
		String station = "station 1";
		List<FireStation> fireStations = new ArrayList<>();

		FireStation fireStation1 = new FireStation();
		fireStation1.setAddress("address 1");
		fireStation1.setStation("station 1");

		Medication medication = new Medication();
		Allergy allergy = new Allergy();
		MedicalRecord medicalRecord1 = new MedicalRecord();
		List<Medication> medications = new ArrayList<>();
		medications.add(medication);
		List<Allergy> allergies = new ArrayList<>();
		allergies.add(allergy);
		medicalRecord1.setMedications(medications);
		medicalRecord1.setAllergies(allergies);
		medicalRecord1.setFirstName("John");
		medicalRecord1.setLastName("Doe");
		medicalRecord1.setBirthDate("01/01/1990");
		Person person1 = new Person();
		person1.setFirstName("John");
		person1.setLastName("Doe");
		person1.setAddress("address 1");
		person1.setPhone("phone 1");
		person1.setMedicalRecord(medicalRecord1);
		Person person2 = new Person();
		person2.setFirstName("Jane");
		person2.setLastName("Doe");
		person2.setAddress("address 1");
		person2.setPhone("phone 2");
		MedicalRecord medicalRecord2 = new MedicalRecord();
		medicalRecord2.setMedications(medications);
		medicalRecord2.setAllergies(allergies);
		medicalRecord2.setFirstName("Jane");
		medicalRecord2.setLastName("Doe");
		medicalRecord2.setBirthDate("01/01/2012");
		person2.setMedicalRecord(medicalRecord2);
		List<Person> persons = new ArrayList<>();
		persons.add(person1);
		persons.add(person2);
		fireStation1.setPersons(persons);
		fireStations.add(fireStation1);

		when(fireStationRepository.findAllByStation(station)).thenReturn(fireStations);

		// When
		java.util.Map<String, List<CommonDTO>> response = urlService.fireStationURL(station);

		// Then
		assertNotNull(response);
		assertTrue(response.containsKey("Adults: 1, Enfants: 1"));
		verify(fireStationRepository).findAllByStation(station);
	}

	@Test
	void childAlertNoChildrenTest() {
		// Given
		String address = "address";
		List<Person> personsAtAddress = new ArrayList<>();
		Person child = new Person();
		child.setFirstName("John");
		child.setLastName("Doe");
		child.setAddress("address");
		Person adult = new Person();
		adult.setFirstName("Jane");
		adult.setLastName("Doe");
		adult.setAddress("address");

		personsAtAddress.add(child);
		personsAtAddress.add(adult);

		MedicalRecord childMedicalRecord = new MedicalRecord();
		childMedicalRecord.setFirstName("John");
		childMedicalRecord.setLastName("Doe");
		childMedicalRecord.setBirthDate("01/01/1990");
		MedicalRecord adultMedicalRecord = new MedicalRecord();
		adultMedicalRecord.setFirstName("Jane");
		adultMedicalRecord.setLastName("Doe");
		adultMedicalRecord.setBirthDate("01/01/1990");

		when(personRepository.findAllByAddress(address)).thenReturn(personsAtAddress);
		when(medicalRecordRepository.findByFirstNameAndLastName("John", "Doe"))
				.thenReturn(Optional.of(childMedicalRecord));
		when(medicalRecordRepository.findByFirstNameAndLastName("Jane", "Doe"))
				.thenReturn(Optional.of(adultMedicalRecord));
		when(fireDTOMapper.convertPatientToChildAlertDTO(any(MedicalRecord.class))).thenAnswer(invocation -> {
			MedicalRecord record = invocation.getArgument(0);
			return new CommonDTO(record.getFirstName(), record.getLastName(), null, null, null,
					record.calculateAge(record.getBirthDate()), null, null);
		});

		// When
		ChildAlertResponsDTO response = urlService.childAlert(address);

		// Then
		assertNull(response);

	}

	@Test
	void fireNotRegisteredAddressTest() {
		// Given
		String address = "123 Maple St";
		Optional<FireStation> optionalFireStation = Optional.empty();

		List<Person> personsAtAddress = new ArrayList<>();
		Person person1 = new Person();
		person1.setFirstName("John");
		person1.setLastName("Doe");
		person1.setAddress(address);
		person1.setPhone("111-222-3333");
		personsAtAddress.add(person1);

		MedicalRecord medicalRecord1 = new MedicalRecord();
		medicalRecord1.setFirstName("John");
		medicalRecord1.setLastName("Doe");
		medicalRecord1.setBirthDate("01/01/1990");

		List<CommonDTO> fireDTOs = new ArrayList<>();
		CommonDTO fireDTO = new CommonDTO("John", "Doe", "phone", null, null, 30, null, null);
		fireDTOs.add(fireDTO);

		when(fireStationRepository.findByAddress(address)).thenReturn(optionalFireStation);

		// When
		FireResponsDTO response = urlService.fire(address);

		// Then
		assertNull(response);

	}

	@Test
	void personInfoPersonNotfoundTest() {
		// Given
		String firstName = "John", lastName = "Doe";
		List<Person> persons = new ArrayList<>();
		Person person = new Person();
		person.setFirstName("John");
		person.setLastName("Doe");
		person.setEmail("email");
		persons.add(person);
		when(personRepository.findAllByLastName(lastName)).thenReturn(persons);
		when(personRepository.findByFirstNameAndLastName(firstName, lastName)).thenReturn(Optional.empty());

		// When
		PersonInfoResponsDTO result = urlService.personInfo(firstName, lastName);

		// Then
		assertNull(result);
	}
}
