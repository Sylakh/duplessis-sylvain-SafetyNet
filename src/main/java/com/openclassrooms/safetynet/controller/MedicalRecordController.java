package com.openclassrooms.safetynet.controller;

import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.safetynet.DTO.MedicalRecordDTO;
import com.openclassrooms.safetynet.service.MedicalRecordService;

@RestController
public class MedicalRecordController {

	private static final Logger logger = LogManager.getLogger("MedicalRecordController");

	@Autowired
	private MedicalRecordService medicalRecordService;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@GetMapping("/patientall")
	public @ResponseBody List<Map<String, Object>> getAllPatients() {
		String sql = "SELECT p.*, m.medication_name, a.allergy_name " + "FROM Patient p "
				+ "LEFT JOIN Medication m ON p.patient_id = m.patient_id "
				+ "LEFT JOIN Allergie a ON p.patient_id = a.patient_id";

		List<Map<String, Object>> patients = jdbcTemplate.queryForList(sql);
		return patients;
	}

	@PostMapping("/medicalrecord")
	public MedicalRecordDTO createMedicalRecord(@RequestBody MedicalRecordDTO medicalRecordDTO) {
		logger.info("creation process of a new medical records requested");
		return medicalRecordService.createMedicalRecord(medicalRecordDTO);
	}

	@DeleteMapping("/medicalrecord/{firstName}/{lastName}")
	public void deletePatientByFirstNameAndLastName(@PathVariable("firstName") String firstName,
			@PathVariable("lastName") String lastName) throws Exception {
		logger.info("delete patient process by firstname and lastname requested");
		medicalRecordService.deletePatientByFirstNameAndLastName(firstName, lastName);
	}

	@PutMapping("/medicalrecord")
	public MedicalRecordDTO updateMedicalRecord(@RequestBody MedicalRecordDTO medicalRecordDTO) {
		logger.info("update process of a medical records requested");
		return medicalRecordService.updateMedicalRecord(medicalRecordDTO);

	}

}
