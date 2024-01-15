package com.openclassrooms.safetynet.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.openclassrooms.safetynet.DTO.MedicalRecordDTO;
import com.openclassrooms.safetynet.model.Allergy;
import com.openclassrooms.safetynet.model.MedicalRecord;
import com.openclassrooms.safetynet.model.Medication;
import com.openclassrooms.safetynet.model.Patient;

@Component
public class MedicalRecordMapper {

	public MedicalRecordDTO convertToDTO(MedicalRecord medicalRecord) {

		// Extraction des informations du patient
		Patient patient = medicalRecord.getPatient();
		String firstName = patient.getFirstName();
		String lastName = patient.getLastName();
		String birthDate = patient.getBirthDate();

		// Extraction des médicaments
		List<String> medications = medicalRecord.getListMedication().stream().map(Medication::getMedication)
				.collect(Collectors.toList());

		// Extraction des allergies
		List<String> allergies = medicalRecord.getListAllergy().stream().map(Allergy::getAllergyName)
				.collect(Collectors.toList());

		// Création de l'objet MedicalRecordDTO
		return new MedicalRecordDTO(firstName, lastName, birthDate, medications, allergies);
	}

	public MedicalRecord convertFromDTO(MedicalRecordDTO medicalRecordDTO) {

		// Extraction des informations du patient à partir du DTO
		Patient patient = new Patient();
		patient.setFirstName(medicalRecordDTO.firstName());
		patient.setLastName(medicalRecordDTO.lastName());
		patient.setBirthDate(medicalRecordDTO.birthDate());

		// Création de la liste de médicaments à partir des informations du DTO
		List<Medication> medications = medicalRecordDTO.medications().stream().map(medication -> {
			Medication convertMedication = new Medication();
			convertMedication.setMedication(medication);
			// Set other Medication properties if needed
			return convertMedication;
		}).collect(Collectors.toList());

		// Création de la liste d'allergies à partir des informations du DTO
		List<Allergy> allergies = medicalRecordDTO.allergies().stream().map(allergyName -> {
			Allergy allergy = new Allergy();
			allergy.setAllergyName(allergyName);
			// Set other Allergy properties if needed
			return allergy;
		}).collect(Collectors.toList());

		// Création de l'objet MedicalRecord à partir des informations extraites
		MedicalRecord medicalRecord = new MedicalRecord(patient, medications, allergies);
		// medicalRecord.setPatient(patient);
		// medicalRecord.setListMedication(medications);
		// medicalRecord.setListAllergy(allergies);

		return medicalRecord;
	}
}