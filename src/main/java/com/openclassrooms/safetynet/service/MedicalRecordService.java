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
import com.openclassrooms.safetynet.model.Person;
import com.openclassrooms.safetynet.repository.AllergyRepository;
import com.openclassrooms.safetynet.repository.MedicalRecordRepository;
import com.openclassrooms.safetynet.repository.MedicationRepository;
import com.openclassrooms.safetynet.repository.PersonRepository;

import jakarta.transaction.Transactional;
import lombok.Data;

@Data
@Service
public class MedicalRecordService {

	private static final Logger logger = LogManager.getLogger("MedicalRecordService");

	@Autowired
	private MedicalRecordRepository medicalRecordRepository;

	@Autowired
	private MedicationRepository medicationRepository;

	@Autowired
	private AllergyRepository allergyRepository;

	@Autowired
	private MedicalRecordMapper medicalRecordMapper;

	@Autowired
	private PersonRepository personRepository;

	@Transactional
	public void deleteMedicalRecordByFirstNameAndLastName(String firstName, String lastName) throws Exception {

		logger.info("delete medicalRecord process by firstname and lastname begins");
		Optional<MedicalRecord> optionalMedicalRecord = medicalRecordRepository.findByFirstNameAndLastName(firstName,
				lastName);
		if (optionalMedicalRecord.isPresent()) {
			logger.info("medical record by firstname and lastname found");
			MedicalRecord medicalRecordToBeDeleted = optionalMedicalRecord.get();
			Long medicalRecord_id = medicalRecordToBeDeleted.getMedicalRecord_id();
			medicalRecordRepository.deleteById(medicalRecord_id);
			logger.info("medicalrecord by firstname and lastname deleted");
		} else {
			logger.error("MedicalRecord not found");
			throw new Exception("Medicalrecord not found, no data deleted");
		}
	}

	public MedicalRecordDTO createMedicalRecord(MedicalRecordDTO medicalRecordDTO) throws Exception {
		logger.info("creation process of a new medical records begins");

		MedicalRecord medicalRecord = medicalRecordMapper.convertFromDTO(medicalRecordDTO);
		Optional<Person> optionalPerson = personRepository.findByFirstNameAndLastName(medicalRecord.getFirstName(),
				medicalRecord.getLastName());
		logger.info("creation process of a new medical records: mapper done");
		if (optionalPerson.isPresent()) {
			Person personFound = optionalPerson.get();
			medicalRecord.setPerson(personFound);
			MedicalRecord savedMedicalRecord = medicalRecordRepository.save(medicalRecord);

			List<Medication> listMedication = medicalRecord.getMedication();
			List<Medication> savedListMedication = new ArrayList<>();
			for (Medication medication : listMedication) {
				medication.setMedicalRecord(medicalRecord);
				savedListMedication.add(medicationRepository.save(medication));
			}

			List<Allergy> listAllergy = medicalRecord.getAllergy();
			List<Allergy> savedListAllergy = new ArrayList<>();
			for (Allergy allergy : listAllergy) {
				allergy.setMedicalRecord(medicalRecord);
				savedListAllergy.add(allergyRepository.save(allergy));
			}

			logger.info("creation process of a new medical records done");
			return medicalRecordMapper.convertToDTO(savedMedicalRecord);
		} else {
			logger.error("creation of medicalRecord impossible, person not present");
			throw new Exception("Person not found, new data not recorded");
		}
	}

	@Transactional
	public MedicalRecordDTO updateMedicalRecord(MedicalRecordDTO medicalRecordDTO) throws Exception {
		logger.info("update process of a medical records begins");
		MedicalRecord unCompleteMedicalRecord = medicalRecordMapper.convertFromDTO(medicalRecordDTO);
		String firstName = unCompleteMedicalRecord.getFirstName();
		String lastName = unCompleteMedicalRecord.getLastName();
		String birthDate = unCompleteMedicalRecord.getBirthDate();
		Optional<MedicalRecord> optionalMedicalRecord = medicalRecordRepository.findByFirstNameAndLastName(firstName,
				lastName);

		if (optionalMedicalRecord.isPresent()) {
			MedicalRecord medicalRecordFound = optionalMedicalRecord.get();
			logger.info("medicalrecord to update found");
			logger.info("medicalrecordfound id:" + medicalRecordFound.getMedicalRecord_id());
			medicalRecordFound.setBirthDate(birthDate);

			// Directly delete old Allergies and Medications
			List<Allergy> allergiesToDelete = allergyRepository.findAllByMedicalRecord(medicalRecordFound);
			allergiesToDelete.forEach(allergy -> {
				logger.info("allergy to be deleted id:" + allergy.getId());
				allergyRepository.deleteById(allergy.getId());
			});
			logger.info("old Allergy deleted");

			List<Medication> medicationsToDelete = medicationRepository.findAllByMedicalRecord(medicalRecordFound);
			medicationsToDelete.forEach(medication -> {
				logger.info("medication to be deleted id:" + medication.getId());
				medicationRepository.deleteById(medication.getId());
			});
			logger.info("old medication deleted");

			// Set new Allergies and Medications

			List<Medication> savedListMedication = new ArrayList<>();
			for (Medication medication : unCompleteMedicalRecord.getMedication()) {
				medication.setMedicalRecord(medicalRecordFound);
				savedListMedication.add(medicationRepository.save(medication));
			}

			List<Allergy> savedListAllergy = new ArrayList<>();
			for (Allergy allergy : unCompleteMedicalRecord.getAllergy()) {
				allergy.setMedicalRecord(medicalRecordFound);
				savedListAllergy.add(allergyRepository.save(allergy));
			}

			medicalRecordRepository.save(medicalRecordFound);
			medicalRecordFound.setMedication(unCompleteMedicalRecord.getMedication());
			medicalRecordFound.setAllergy(unCompleteMedicalRecord.getAllergy());

			// Save updated MedicalRecord
			medicalRecordRepository.save(medicalRecordFound);
			logger.info("update process done");
			return medicalRecordMapper.convertToDTO(medicalRecordFound);
		} else {
			logger.error("medicalrecord not found");
			throw new Exception("MedicalRecord not found, new data not updated");
		}
	}
}
