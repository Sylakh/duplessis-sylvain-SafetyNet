package com.openclassrooms.safetynet.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.openclassrooms.safetynet.DTO.ChildAlertDTO;
import com.openclassrooms.safetynet.DTO.ChildAlertResponsDTO;
import com.openclassrooms.safetynet.mapper.ChildAlertMapper;
import com.openclassrooms.safetynet.model.FireStation;
import com.openclassrooms.safetynet.model.Patient;
import com.openclassrooms.safetynet.model.Person;
import com.openclassrooms.safetynet.repository.FireStationRepository;
import com.openclassrooms.safetynet.repository.PatientRepository;
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
	private PatientRepository patientRepository;

	@Autowired
	private ChildAlertMapper childAlertMapper;

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
			Optional<Patient> optionalPatient = patientRepository.findByFirstNameAndLastName(person.getFirstName(),
					person.getLastName());
			if (optionalPatient.isPresent()) {
				Patient patient = optionalPatient.get();
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

}
