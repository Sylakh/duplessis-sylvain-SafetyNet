package com.openclassrooms.safetynet.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.safetynet.DTO.ChildAlertResponsDTO;
import com.openclassrooms.safetynet.mapper.ChildAlertMapper;
import com.openclassrooms.safetynet.service.FireStationService;
import com.openclassrooms.safetynet.service.PatientService;
import com.openclassrooms.safetynet.service.PersonService;
import com.openclassrooms.safetynet.service.URLService;

@RestController
public class URLController {

	private static final Logger logger = LogManager.getLogger("URLController");

	@Autowired
	private FireStationService fireStationService;

	@Autowired
	private PersonService personService;

	@Autowired
	private PatientService patientService;

	@Autowired
	private ChildAlertMapper childAlertMapper;

	@Autowired
	private URLService uRLService;

	/**
	 * http://localhost:8080/communityEmail?city=<city> Cette url doit retourner les
	 * adresses mail de tous les habitants de la ville.
	 */

	@GetMapping("/communityEmail")
	public List<String> communityEmail(@RequestParam String city) {
		logger.info("Request communityEmail sent for city " + city);
		return uRLService.communityEmail(city);
	}

	/**
	 * http://localhost:8080/phoneAlert?firestation=<firestation_number> Cette url
	 * doit retourner une liste des numéros de téléphone des résidents desservis par
	 * la caserne de pompiers.
	 */
	@GetMapping("/phoneAlert")
	public List<String> phoneAlert(@RequestParam String station) {
		logger.info("Request phoneAlert sent for firestation " + station);
		return uRLService.phoneAlert(station);
	}

	/**
	 * http://localhost:8080/childAlert?address=<address> Cette url doit retourner
	 * une liste d'enfants (tout individu âgé de 18 ans ou moins) habitant à cette
	 * adresse. La liste doit comprendre le prénom et le nom de famille de chaque
	 * enfant, son âge et une liste des autres membres du foyer. S'il n'y a pas
	 * d'enfant, cette url peut renvoyer une chaîne vide.
	 */
	@GetMapping("/childAlert")
	public ChildAlertResponsDTO childAlert(@RequestParam String address) {
		logger.info("Request childAlert sent for address " + address);
		return uRLService.childAlert(address);
	}

	/**
	 * http://localhost:8080/fire?address=<address> Cette url doit retourner la
	 * liste des habitants vivant à l’adresse donnée ainsi que le numéro de la
	 * caserne de pompiers la desservant. La liste doit inclure le nom, le numéro de
	 * téléphone, l'âge et les antécédents médicaux (médicaments, posologie et
	 * allergies) de chaque personne.
	 */
	@GetMapping("/fire")
	public FireResponsDTO fire(@RequestParam String address) {
		logger.info("Request fire sent for address " + address);
		return uRLService.fire(address);
	}

}
