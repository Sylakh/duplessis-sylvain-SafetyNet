package com.openclassrooms.safetynet.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.openclassrooms.safetynet.DTO.MedicalRecordDTO;
import com.openclassrooms.safetynet.mapper.MedicalRecordMapper;
import com.openclassrooms.safetynet.model.Allergy;
import com.openclassrooms.safetynet.model.MedicalRecord;
import com.openclassrooms.safetynet.model.Medication;
import com.openclassrooms.safetynet.model.Patient;
import com.openclassrooms.safetynet.repository.AllergyRepository;
import com.openclassrooms.safetynet.repository.MedicationRepository;
import com.openclassrooms.safetynet.repository.PatientRepository;

import lombok.Data;

@Data
@Service
public class MedicalRecordService {

	private static final Logger logger = LogManager.getLogger("MedicalRecordService");

	@Autowired
	private PatientRepository patientRepository;

	@Autowired
	private MedicationRepository medicationRepository;

	@Autowired
	private AllergyRepository allergyRepository;

	@Autowired
	private MedicalRecordMapper medicalRecordMapper;

	public MedicalRecord saveMedicalRecord(MedicalRecord medicalRecord) {

		Patient patient = medicalRecord.getPatient();
		Patient savedPatient = patientRepository.save(patient);

		List<Medication> listMedication = medicalRecord.getListMedication();
		List<Medication> savedListMedication = new ArrayList<>();
		for (Medication medication : listMedication) {
			medication.setPatient(patient);
			savedListMedication.add(medicationRepository.save(medication));
		}

		List<Allergy> listAllergy = medicalRecord.getListAllergy();
		List<Allergy> savedListAllergy = new ArrayList<>();
		for (Allergy allergy : listAllergy) {
			allergy.setPatient(patient);
			savedListAllergy.add(allergyRepository.save(allergy));
		}
		return new MedicalRecord(savedPatient, savedListMedication, savedListAllergy);

	}

	public MedicalRecordDTO createMedicalRecord(MedicalRecordDTO medicalRecordDTO) {
		logger.info("creation process of a new medical records begins");
		MedicalRecord medicalRecord = medicalRecordMapper.convertFromDTO(medicalRecordDTO);

		Patient patient = medicalRecord.getPatient();
		Patient savedPatient = patientRepository.save(patient);

		List<Medication> listMedication = medicalRecord.getListMedication();
		List<Medication> savedListMedication = new ArrayList<>();
		for (Medication medication : listMedication) {
			medication.setPatient(patient);
			savedListMedication.add(medicationRepository.save(medication));
		}

		List<Allergy> listAllergy = medicalRecord.getListAllergy();
		List<Allergy> savedListAllergy = new ArrayList<>();
		for (Allergy allergy : listAllergy) {
			allergy.setPatient(patient);
			savedListAllergy.add(allergyRepository.save(allergy));
		}
		MedicalRecord savedMedicalRecord = new MedicalRecord(savedPatient, savedListMedication, savedListAllergy);
		logger.info("creation process of a new medical records done");
		return medicalRecordMapper.convertToDTO(savedMedicalRecord);
	}

	public void deletePatientByFirstNameAndLastName(String firstName, String lastName) throws Exception {

		logger.info("delete patient process by firstname and lastname begins");
		Optional<Patient> optionalPatient = patientRepository.findByFirstNameAndLastName(firstName, lastName);
		if (optionalPatient.isPresent()) {
			logger.info("patient by firstname and lastname found");
			Patient patientToBeDeleted = optionalPatient.get();
			Long patient_id = patientToBeDeleted.getPatient_id();
			patientRepository.deleteById(patient_id);
			logger.info("patient by firstname and lastname deleted");
		}
	}

	public MedicalRecordDTO updateMedicalRecord(MedicalRecordDTO medicalRecordDTO) {
		logger.info("update process of a medical records begins");
		MedicalRecord unCompleteMedicalRecord = medicalRecordMapper.convertFromDTO(medicalRecordDTO);
		String firstName = unCompleteMedicalRecord.getPatient().getFirstName();
		String lastName = unCompleteMedicalRecord.getPatient().getLastName();
		String birthDate = unCompleteMedicalRecord.getPatient().getBirthDate();
		Optional<Patient> optionalPatient = patientRepository.findByFirstNameAndLastName(firstName, lastName);
		if (optionalPatient.isPresent()) {
			Patient patientFound = optionalPatient.get();
			logger.info("patient to update found");
			patientFound.setBirthDate(birthDate);
			patientRepository.save(patientFound);
			unCompleteMedicalRecord.setPatient(patientFound);

			// delete old Allergy
			List<Allergy> listAllergyToBeDeleted = allergyRepository.findAllByPatient(patientFound);
			for (Allergy allergy : listAllergyToBeDeleted) {
				allergyRepository.deleteById(allergy.getAllergy_id());
			}
			// delete old Medication
			List<Medication> listMedicationToBeDeleted = medicationRepository.findAllByPatient(patientFound);
			for (Medication medication : listMedicationToBeDeleted) {
				medicationRepository.deleteById(medication.getId());
			}

			List<Medication> listMedication = unCompleteMedicalRecord.getListMedication();
			List<Medication> savedListMedication = new ArrayList<>();
			for (Medication medication : listMedication) {
				medication.setPatient(patientFound);
				savedListMedication.add(medicationRepository.save(medication));
			}

			List<Allergy> listAllergy = unCompleteMedicalRecord.getListAllergy();
			List<Allergy> savedListAllergy = new ArrayList<>();
			for (Allergy allergy : listAllergy) {
				allergy.setPatient(patientFound);
				savedListAllergy.add(allergyRepository.save(allergy));
			}
			logger.info("update process done");
			return medicalRecordMapper.convertToDTO(unCompleteMedicalRecord);
		} else {
			logger.info("update process impossible, patient not found");
			return null;
		}

	}

}
