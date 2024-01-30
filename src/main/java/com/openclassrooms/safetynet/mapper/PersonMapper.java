package com.openclassrooms.safetynet.mapper;

import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.openclassrooms.safetynet.DTO.PersonDTO;
import com.openclassrooms.safetynet.model.FireStation;
import com.openclassrooms.safetynet.model.Person;
import com.openclassrooms.safetynet.repository.FireStationRepository;
import com.openclassrooms.safetynet.repository.MedicalRecordRepository;

@Component
public class PersonMapper {

	private static final Logger logger = LogManager.getLogger("PersonMapper");

	@Autowired
	private FireStationRepository fireStationRepository;

	@Autowired
	MedicalRecordRepository medicalRecordRepository;

	public PersonDTO convertPersonToPersonDTO(Person person) {
		return new PersonDTO(person.getFirstName(), person.getLastName(), person.getAddress(), person.getCity(),
				person.getZip(), person.getPhone(), person.getEmail());
	}

	public Person convertPersonFromPersonDTO(PersonDTO personDTO) {
		logger.info("debut conversion person from DTO");
		Optional<FireStation> optionalFireStation = fireStationRepository.findByAddress(personDTO.address());
		if (optionalFireStation.isPresent()) {
			logger.info("firestation found");
			FireStation fireStationFound = optionalFireStation.get();
			Person person = new Person();
			person.setFirstName(personDTO.firstName());
			person.setLastName(personDTO.lastName());
			person.setAddress(personDTO.address());
			person.setCity(personDTO.city());
			person.setZip(personDTO.zip());
			person.setPhone(personDTO.phone());
			person.setEmail(personDTO.email());
			person.setName(personDTO.firstName() + "," + personDTO.lastName());
			person.setFireStation(fireStationFound);
			/**
			 * Optional<MedicalRecord> optionalMedicalRecord = medicalRecordRepository
			 * .findByFirstNameAndLastName(personDTO.firstName(), personDTO.lastName()); if
			 * (optionalMedicalRecord.isPresent()) {
			 * person.setMedicalRecord(optionalMedicalRecord.get()); } else {
			 * person.setMedicalRecord(null); }
			 */
			return person;
		} else {
			throw new RuntimeException("FireStation not found for the address");

		}
	}

}
