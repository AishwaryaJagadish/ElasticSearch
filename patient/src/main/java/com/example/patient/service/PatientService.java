package com.example.patient.service;


import com.example.patient.model.OutputModel;
import com.example.patient.model.Patient;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface PatientService {

	public Patient createPatient(Patient doctor);


	public List<Patient> getAll();
	
	public ResponseEntity<?> updatePatient(Patient doctor,String id);


	public ResponseEntity<?> deletePatient(String id);
	
	public Patient getSinglePatient(String id) throws Exception;


	public OutputModel getOutput(String id);


}
