package com.openclassrooms.safetynet.controller;

import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.safetynet.DTO.ChildAlertResponsDTO;
import com.openclassrooms.safetynet.DTO.CommonDTO;
import com.openclassrooms.safetynet.DTO.FireResponsDTO;
import com.openclassrooms.safetynet.DTO.FloodStationPersonDTO;
import com.openclassrooms.safetynet.DTO.PersonInfoResponsDTO;
import com.openclassrooms.safetynet.service.URLService;

@RestController
public class URLController {

	private static final Logger logger = LogManager.getLogger("URLController");

	@Autowired
	private URLService urlService;

	/**
	 * http://localhost:8080/communityEmail?city=<city> Cette url doit retourner les
	 * adresses mail de tous les habitants de la ville.
	 */

	@GetMapping("/communityEmail")
	public List<String> communityEmail(@RequestParam String city) {
		logger.info("Request communityEmail sent for city " + city);
		return urlService.communityEmail(city);
	}

	/**
	 * http://localhost:8080/phoneAlert?firestation=<firestation_number> Cette url
	 * doit retourner une liste des numéros de téléphone des résidents desservis par
	 * la caserne de pompiers.
	 */
	@GetMapping("/phoneAlert")
	public List<String> phoneAlert(@RequestParam String station) {
		logger.info("Request phoneAlert sent for firestation " + station);
		return urlService.phoneAlert(station);
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
		return urlService.childAlert(address);
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
		return urlService.fire(address);
	}

	/**
	 * http://localhost:8080/personInfo?firstName=<firstName>&lastName=<lastName>
	 * Cette url doit retourner le nom, l'adresse, l'âge, l'adresse mail et les
	 * antécédents médicaux (médicaments, posologie, allergies) de chaque habitant.
	 * Si plusieurs personnes portent le même nom, elles doivent toutes apparaître.
	 */
	@GetMapping("/personInfo")
	public PersonInfoResponsDTO personInfo(@RequestParam String firstName, @RequestParam String lastName) {
		logger.info("Request personInfo sent for " + firstName + " " + lastName);
		return urlService.personInfo(firstName, lastName);
	}

	/**
	 * http://localhost:8080/flood/stations?stations=<a list of station_numbers>
	 * Cette url doit retourner une liste de tous les foyers desservis par la
	 * caserne. Cette liste doit regrouper les personnes par adresse. Elle doit
	 * aussi inclure le nom, le numéro de téléphone et l'âge des habitants, et faire
	 * figurer leurs antécédents médicaux (médicaments, posologie et allergies) à
	 * côté de chaque nom.
	 */

	@GetMapping("/flood/stations")
	public Map<String, List<FloodStationPersonDTO>> floodStations(@RequestParam String[] stations) {
		logger.info("Request flood/stations sent ");
		return urlService.floodStations(stations);
	}

	/**
	 * http://localhost:8080/firestation?stationNumber=<station_number> Cette url
	 * doit retourner une liste des personnes couvertes par la caserne de pompiers
	 * correspondante. Donc, si le numéro de station = 1, elle doit renvoyer les
	 * habitants couverts par la station numéro 1. La liste doit inclure les
	 * informations spécifiques suivantes : prénom, nom, adresse, numéro de
	 * téléphone. De plus, elle doit fournir un décompte du nombre d'adultes et du
	 * nombre d'enfants (tout individu âgé de 18 ans ou moins) dans la zone
	 * desservie.
	 */
	@GetMapping("/firestation")
	public Map<String, List<CommonDTO>> fireStation(String stationNumber) {
		logger.info("Request firestation sent for station " + stationNumber);
		return urlService.fireStationURL(stationNumber);
	}
}
