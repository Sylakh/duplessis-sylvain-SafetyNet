package com.openclassrooms.safetynet.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import com.openclassrooms.safetynet.DTO.MedicalRecordDTO;
import com.openclassrooms.safetynet.model.Allergy;
import com.openclassrooms.safetynet.model.MedicalRecord;
import com.openclassrooms.safetynet.model.Medication;

@Component
public class MedicalRecordMapper {

	private static final Logger logger = LogManager.getLogger("MedicalRecordMapper");

	public MedicalRecordDTO convertToDTO(MedicalRecord medicalRecord) {

		// Extraction des informations du medicalRecord
		String firstName = medicalRecord.getFirstName();
		String lastName = medicalRecord.getLastName();
		String birthDate = medicalRecord.getBirthDate();

		// Extraction des médicaments
		List<String> medications = medicalRecord.getMedication().stream().map(Medication::getMedication)
				.collect(Collectors.toList());

		// Extraction des allergies
		List<String> allergies = medicalRecord.getAllergy().stream().map(Allergy::getAllergyName)
				.collect(Collectors.toList());

		// Création de l'objet MedicalRecordDTO
		return new MedicalRecordDTO(firstName, lastName, birthDate, medications, allergies);
	}

	public MedicalRecord convertFromDTO(MedicalRecordDTO medicalRecordDTO) {

		// Extraction des informations du patient à partir du DTO
		MedicalRecord medicalRecord = new MedicalRecord();
		medicalRecord.setFirstName(medicalRecordDTO.firstName());
		medicalRecord.setLastName(medicalRecordDTO.lastName());
		medicalRecord.setName(medicalRecordDTO.firstName() + "," + medicalRecordDTO.lastName());
		medicalRecord.setBirthDate(medicalRecordDTO.birthDate());
		medicalRecord.setPerson(null);
		// Création de la liste de médicaments à partir des informations du DTO
		List<Medication> medications = medicalRecordDTO.medications().stream().map(medication -> {
			Medication convertMedication = new Medication();
			convertMedication.setMedication(medication);
			return convertMedication;
		}).collect(Collectors.toList());

		medicalRecord.setMedication(medications);

		// Création de la liste d'allergies à partir des informations du DTO
		List<Allergy> allergies = medicalRecordDTO.allergies().stream().map(allergyName -> {
			Allergy allergy = new Allergy();
			allergy.setAllergyName(allergyName);
			// Set other Allergy properties if needed
			return allergy;
		}).collect(Collectors.toList());

		medicalRecord.setAllergy(allergies);

		return medicalRecord;

	}
}