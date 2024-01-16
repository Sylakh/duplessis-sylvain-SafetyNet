package com.openclassrooms.safetynet.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

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
import com.openclassrooms.safetynet.mapper.MedicalRecordMapper;
import com.openclassrooms.safetynet.model.Allergy;
import com.openclassrooms.safetynet.model.MedicalRecord;
import com.openclassrooms.safetynet.model.Medication;
import com.openclassrooms.safetynet.model.Patient;
import com.openclassrooms.safetynet.service.AllergyService;
import com.openclassrooms.safetynet.service.MedicalRecordService;
import com.openclassrooms.safetynet.service.MedicationService;
import com.openclassrooms.safetynet.service.PatientService;

@RestController
public class MedicalRecordController {

	private static final Logger logger = LogManager.getLogger("MedicalRecordController");

	@Autowired
	private MedicalRecordService medicalRecordService;

	@Autowired
	private MedicalRecordMapper medicalRecordMapper;

	@Autowired
	private PatientService patientService;

	@Autowired
	private MedicationService medicationService;

	@Autowired
	private AllergyService allergyService;

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

	@PostMapping("/medicalrecord")
	public MedicalRecordDTO createMedicalRecord(@RequestBody MedicalRecordDTO medicalRecordDTO) {
		logger.info("creation process of a new medical records begins");
		MedicalRecord medicalRecord = medicalRecordMapper.convertFromDTO(medicalRecordDTO);
		MedicalRecord savedMedicalRecord = medicalRecordService.saveMedicalRecord(medicalRecord);
		return medicalRecordMapper.convertToDTO(savedMedicalRecord);
	}

	/**
	 * Delete - Delete a patient
	 * 
	 * @param lastName and firstName of an object patient
	 * @throws Exception
	 * 
	 * 
	 */

	@DeleteMapping("/medicalrecord/{firstName}/{lastName}")
	public void deletePatientByFirstNameAndLastName(@PathVariable("firstName") String firstName,
			@PathVariable("lastName") String lastName) throws Exception {

		logger.info("delete patient process by firstname and lastname begins");
		Optional<Patient> optionalPatient = patientService.findPatientByFirstNameAndLastName(firstName, lastName);
		if (optionalPatient.isPresent()) {
			logger.info("patient by firstname and lastname found");
			Patient patientToBeDeleted = optionalPatient.get();

			List<Allergy> listAllergyToBeDeleted = allergyService.findAllAllergyByPatient(patientToBeDeleted);
			for (Allergy allergy : listAllergyToBeDeleted) {
				allergyService.deleteAllergyById(allergy.getAllergy_id());
			}
			logger.info("allergy deleted");

			List<Medication> listMedicationToBeDeleted = medicationService
					.findAllMedicationByPatient(patientToBeDeleted);
			for (Medication medication : listMedicationToBeDeleted) {
				medicationService.deleteMedicationById(medication.getId());
			}
			logger.info("medication deleted");

			Long patient_id = patientToBeDeleted.getPatient_id();
			patientService.deletePatient(patient_id);
			logger.info("patient by firstname and lastname deleted");
		}
	}

	@PutMapping("/medicalrecord")
	public MedicalRecordDTO updateMedicalRecord(@RequestBody MedicalRecordDTO medicalRecordDTO) {
		logger.info("update process of a medical records begins");
		MedicalRecord unCompleteMedicalRecord = medicalRecordMapper.convertFromDTO(medicalRecordDTO);
		String firstName = unCompleteMedicalRecord.getPatient().getFirstName();
		String lastName = unCompleteMedicalRecord.getPatient().getLastName();
		String birthDate = unCompleteMedicalRecord.getPatient().getBirthDate();
		Optional<Patient> optionalPatient = patientService.findPatientByFirstNameAndLastName(firstName, lastName);
		if (optionalPatient.isPresent()) {
			Patient patientFound = optionalPatient.get();
			logger.info("patient to update found");
			patientFound.setBirthDate(birthDate);
			unCompleteMedicalRecord.setPatient(patientFound);
			// delete old Allergy
			List<Allergy> listAllergyToBeDeleted = allergyService.findAllAllergyByPatient(patientFound);
			for (Allergy allergy : listAllergyToBeDeleted) {
				allergyService.deleteAllergyById(allergy.getAllergy_id());
			}
			// delete old Medication
			List<Medication> listMedicationToBeDeleted = medicationService.findAllMedicationByPatient(patientFound);
			for (Medication medication : listMedicationToBeDeleted) {
				medicationService.deleteMedicationById(medication.getId());
			}
			medicalRecordService.saveMedicalRecord(unCompleteMedicalRecord);
			logger.info("update process done");
			return medicalRecordMapper.convertToDTO(unCompleteMedicalRecord);
		} else {
			logger.info("update process impossible, patient not found");
			return null;
		}

	}

}
