package com.openclassrooms.safetynet.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.safetynet.model.Patient;
import com.openclassrooms.safetynet.service.PatientService;

@RestController
public class PatientController {

	private static final Logger logger = LogManager.getLogger("PatientController");

	@Autowired
	private PatientService patientService;

	@GetMapping("/medicalrecord")
	public Iterable<Patient> getAllMedicalRecord() {
		logger.info("Get all patients from database");
		return patientService.getAllPatient();
	}

}
