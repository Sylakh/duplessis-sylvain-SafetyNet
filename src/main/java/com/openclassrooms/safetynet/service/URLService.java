package com.openclassrooms.safetynet.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.openclassrooms.safetynet.DTO.ChildAlertDTO;
import com.openclassrooms.safetynet.DTO.ChildAlertResponsDTO;
import com.openclassrooms.safetynet.DTO.FireDTO;
import com.openclassrooms.safetynet.DTO.FireResponsDTO;
import com.openclassrooms.safetynet.DTO.FireStationURLDTO;
import com.openclassrooms.safetynet.DTO.FloodStationPersonDTO;
import com.openclassrooms.safetynet.DTO.PersonInfoDTO;
import com.openclassrooms.safetynet.DTO.PersonInfoResponsDTO;
import com.openclassrooms.safetynet.mapper.ChildAlertMapper;
import com.openclassrooms.safetynet.mapper.FireDTOMapper;
import com.openclassrooms.safetynet.mapper.PersonInfoDTOMapper;
import com.openclassrooms.safetynet.model.Allergy;
import com.openclassrooms.safetynet.model.FireStation;
import com.openclassrooms.safetynet.model.MedicalRecord;
import com.openclassrooms.safetynet.model.Medication;
import com.openclassrooms.safetynet.model.Person;
import com.openclassrooms.safetynet.repository.FireStationRepository;
import com.openclassrooms.safetynet.repository.MedicalRecordRepository;
import com.openclassrooms.safetynet.repository.PersonRepository;

import lombok.Data;

@Data
@Service
public class URLService {

	private static final Logger logger = LogManager.getLogger("URLService");

	@Autowired
	private PersonRepository personRepository;

	@Autowired
	private FireStationRepository fireStationRepository;

	@Autowired
	private MedicalRecordRepository patientRepository;

	@Autowired
	private ChildAlertMapper childAlertMapper;

	@Autowired
	private FireDTOMapper fireDTOMapper;

	@Autowired
	private PersonInfoDTOMapper personInfoDTOMapper;

	public List<String> communityEmail(String city) {
		logger.info("Request communityEmail sent for city " + city);

		List<String> communityEmail = new ArrayList<>();
		List<Person> community = new ArrayList<>();
		community = personRepository.findAllByCity(city);
		for (Person person : community) {
			communityEmail.add(person.getEmail());
		}
		logger.info("Request communityEmail done");
		return communityEmail;
	}

	public List<String> phoneAlert(String station) {
		logger.info("Request phoneAlert sent for firestation " + station);
		List<String> listPhone = new ArrayList<>();
		List<FireStation> listAddress = fireStationRepository.findByStation(station);
		List<Person> listPersonByAddress = new ArrayList<>();

		for (FireStation fireStation : listAddress) {
			listPersonByAddress = personRepository.findAllByAddress(fireStation.getAddress());
			for (Person person : listPersonByAddress) {
				listPhone.add(person.getPhone());
			}
		}
		logger.info("Request phoneAlert done");
		return listPhone;
	}

	public ChildAlertResponsDTO childAlert(String address) {
		logger.info("Request childAlert sent for address " + address);
		List<ChildAlertDTO> listChildAlertDTO = new ArrayList<>();
		List<Person> listPerson = new ArrayList<>();
		listPerson = personRepository.findAllByAddress(address);
		for (Person person : listPerson) {
			Optional<MedicalRecord> optionalPatient = patientRepository
					.findByFirstNameAndLastName(person.getFirstName(), person.getLastName());
			if (optionalPatient.isPresent()) {
				MedicalRecord patient = optionalPatient.get();
				listChildAlertDTO.add(childAlertMapper.convertPatientToChildAlertDTO(patient));
			} else {
				logger.error(
						"the person " + person.getFirstName() + " " + person.getLastName() + "has no medical records.");
			}
		}

		List<ChildAlertDTO> listChild = new ArrayList<>();
		List<ChildAlertDTO> listAdult = new ArrayList<>();
		for (ChildAlertDTO childAlertDTO : listChildAlertDTO) {
			if (childAlertDTO.age() > 18) {
				listAdult.add(childAlertDTO);
			} else {
				listChild.add(childAlertDTO);
			}
		}
		ChildAlertResponsDTO childAlertRespons = new ChildAlertResponsDTO(listChild, listAdult);
		if (listChild.size() == 0) {
			childAlertRespons = null;
			logger.info("No child at the address " + address);
		}
		logger.info("Request childAlert done");
		return childAlertRespons;
	}

	public FireResponsDTO fire(String address) {
		logger.info("Request fire sent for address " + address);
		Optional<FireStation> optionalFireStation = fireStationRepository.findByAddress(address);
		if (optionalFireStation.isPresent()) {
			FireStation fireStationFound = optionalFireStation.get();
			List<FireDTO> listPersons = fireDTOMapper.convertFireStationIntoFireDTO(fireStationFound);
			return new FireResponsDTO(
					"l'adresse " + address + " est couverte par la caserne " + fireStationFound.getStation() + " .",
					listPersons);
		} else {
			return null;
		}

	}

	public PersonInfoResponsDTO personInfo(String firstName, String lastName) {
		logger.info("Request personinfo sent for " + firstName + " " + lastName);
		List<Person> listPerson = new ArrayList<>();
		listPerson = personRepository.findAllByLastName(lastName);
		Optional<Person> optionalPerson = personRepository.findByFirstNameAndLastName(firstName, lastName);
		PersonInfoDTO personInfoFoundDTO = null;
		if (optionalPerson.isPresent()) {
			Person personFound = optionalPerson.get();
			logger.info("person found");
			personInfoFoundDTO = personInfoDTOMapper.convertPersonToPersonInfoDTO(personFound);
			listPerson.remove(personFound);
		} else {
			logger.info("person not found");
			return null;
		}
		List<String> listFamily = new ArrayList<>();
		for (Person person : listPerson) {
			listFamily.add(person.getFirstName() + " " + person.getLastName());
		}
		return new PersonInfoResponsDTO(personInfoFoundDTO, listFamily);
	}

	public Map<String, List<FloodStationPersonDTO>> floodStations(String[] stations) {
		logger.info("Request flood/stations sent ");
		for (String station : stations) {
			logger.info("Request flood/stations sent for station " + station);
		}
		List<FireStation> allStations = new ArrayList<>();
		List<FireStation> allParticularStation = new ArrayList<>();
		for (String station : stations) {
			allParticularStation = fireStationRepository.findAllByStation(station);
			for (FireStation fireStation : allParticularStation) {
				allStations.add(fireStation);
			}
			allParticularStation = null;
		}
		Map<String, List<FloodStationPersonDTO>> floodStationsRespons = new HashMap<>();

		List<Person> listPerson = new ArrayList<>();
		for (FireStation fireStation : allStations) {
			List<FloodStationPersonDTO> listFloodStationPersonDTO = new ArrayList<>();
			listPerson = fireStation.getPersons();
			for (Person person : listPerson) {
				List<String> medications = new ArrayList<>();
				for (Medication medication : person.getMedicalRecord().getMedication()) {
					medications.add(medication.getMedication());
				}
				List<String> allergies = new ArrayList<>();
				for (Allergy allergy : person.getMedicalRecord().getAllergy()) {
					allergies.add(allergy.getAllergyName());
				}
				listFloodStationPersonDTO
						.add(new FloodStationPersonDTO(person.getFirstName(), person.getLastName(), person.getPhone(),
								person.getMedicalRecord().calculateAge(person.getMedicalRecord().getBirthDate()),
								medications, allergies));
				allergies = null;
				medications = null;
			}
			floodStationsRespons.put(fireStation.getAddress(), listFloodStationPersonDTO);
			// listFloodStationPersonDTO = null;
		}

		return floodStationsRespons;
	}

	public Map<String, List<FireStationURLDTO>> fireStationURL(String station) {
		logger.info("Request firestation sent for station " + station);
		List<FireStation> listFireStation = fireStationRepository.findAllByStation(station);
		List<FireStationURLDTO> listFireStationURLDTO = new ArrayList<>();
		int countAdult = 0;
		int countChildren = 0;
		for (FireStation fireStation : listFireStation) {
			List<Person> listPerson = fireStation.getPersons();
			for (Person person : listPerson) {
				listFireStationURLDTO.add(new FireStationURLDTO(person.getFirstName(), person.getLastName(),
						person.getAddress(), person.getPhone()));
				if (person.getMedicalRecord().calculateAge(person.getMedicalRecord().getBirthDate()) > 18) {
					countAdult += 1;
				} else {
					countChildren += 1;
				}
			}
		}
		Map<String, List<FireStationURLDTO>> map = new HashMap<>();
		String key = "Adults: " + countAdult + ", Enfants: " + countChildren;
		map.put(key, listFireStationURLDTO);
		return map;
	}

}
