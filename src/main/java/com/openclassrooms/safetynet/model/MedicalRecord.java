package com.openclassrooms.safetynet.model;

import java.util.List;

public class MedicalRecord {

	private Patient patient;
	private List<Medication> listMedication;
	private List<Allergy> listAllergy;

	public MedicalRecord(Patient patient, List<Medication> listMedication, List<Allergy> listAllergy) {
		super();
		this.patient = patient;
		this.listMedication = listMedication;
		this.listAllergy = listAllergy;
	}

	public Patient getPatient() {
		return patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}

	public List<Medication> getListMedication() {
		return listMedication;
	}

	public void setListMedication(List<Medication> listMedication) {
		this.listMedication = listMedication;
	}

	public List<Allergy> getListAllergy() {
		return listAllergy;
	}

	public void setListAllergy(List<Allergy> listAllergy) {
		this.listAllergy = listAllergy;
	}

}
