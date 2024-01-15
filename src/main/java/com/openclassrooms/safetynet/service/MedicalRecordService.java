package com.openclassrooms.safetynet.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

	@Autowired
	private PatientRepository patientRepository;

	@Autowired
	private MedicationRepository medicationRepository;

	@Autowired
	private AllergyRepository allergyRepository;

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

}
