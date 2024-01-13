package com.openclassrooms.safetynet.controller;

import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.safetynet.model.MedicalRecord;
import com.openclassrooms.safetynet.service.MedicalRecordService;

@RestController
public class MedicalRecordController {

	private static final Logger logger = LogManager.getLogger("MedicalRecordController");

	@Autowired
	private MedicalRecordService medicalRecordService;

	@GetMapping("/medical")
	public Iterable<MedicalRecord> getAllMedicalRecord() {
		logger.info("request for all medical records");
		return medicalRecordService.getAll();
	}

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@GetMapping("/patientall")
	public @ResponseBody List<Map<String, Object>> getAllPatients() {
		String sql = "SELECT p.*, m.medication_name, m.dosage, a.allergy_name " + "FROM Patient p "
				+ "LEFT JOIN Medication m ON p.patient_id = m.patient_id "
				+ "LEFT JOIN Allergie a ON p.patient_id = a.patient_id";

		List<Map<String, Object>> patients = jdbcTemplate.queryForList(sql);
		return patients;
	}
}
