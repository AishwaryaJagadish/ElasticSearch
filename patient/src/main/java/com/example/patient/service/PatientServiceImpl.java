package com.example.patient.service;


import com.example.patient.model.Hospital;
import com.example.patient.model.OutputModel;
import com.example.patient.model.Patient;
import com.example.patient.model.PatientES;
import com.example.patient.repository.Repo;
import com.example.patient.repository.RepoES;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class PatientServiceImpl implements PatientService {
	
	@Autowired
	private Repo repo;

	@Autowired
	private RepoES repoes;
	
	@Autowired
	private SequenceGenerator seqservice;
	
	Logger logger = LoggerFactory.getLogger(PatientService.class);

	@Override
	public Patient createPatient(Patient patient) {
		patient.setId("P_"+String.valueOf(seqservice.getSequenceNum(Patient.SEQUENCE_NAME)));
		repo.save(patient);
		PatientES patient2 = new PatientES();
		patient2.setAge(patient.getAge());
		patient2.setId(patient.getId());
		patient2.setGender(patient.getGender());
		patient2.setBloodgroup(patient.getBloodgroup());
		patient2.setHid(patient.getHid());
		patient2.setPhone(patient.getPhone());
		patient2.setDepartment(patient.getDepartment());
		patient2.setEmail(patient.getEmail());
		patient2.setName(patient.getName());
		repo.save(patient2);
		return patient;
	}

	@Override
	public List<Patient> getAll() {
		List<Patient> gdoctors = repo.findAll();
		return gdoctors;
	}

	@Override
	public ResponseEntity<?> updatePatient(Patient patient, String id) {
		try {
			Optional<Patient> doctoroptional = repo.findById(id);
			if(doctoroptional.isPresent()) {
			  Patient newobj = doctoroptional.get();
			  if(patient.getName()!=null)
			  newobj.setName(patient.getName());
			  if(patient.getAge()!=0)
			  newobj.setAge(patient.getAge());
			  if(patient.getGender()!=null)
			  newobj.setGender(patient.getGender());
			  if(patient.getBloodgroup()!=null)
			  newobj.setBloodgroup(patient.getBloodgroup());
			  if(patient.getPhone()!=null)
			  newobj.setPhone(patient.getPhone());
			  if(patient.getEmail()!=null)
			  newobj.setEmail(patient.getEmail());
			  repo.save(newobj);
			  return new ResponseEntity<>("Updated the patient in database",HttpStatus.OK);
			}
			else
			{
				logger.error("patient not found in the database");
				return new ResponseEntity<>("patient not found in the database",HttpStatus.NOT_FOUND);
			}
		}
		catch(Exception e)
		{
			logger.error(e.getMessage());
			return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
		}
	}


	@Override
	public ResponseEntity<?> deletePatient(String id){
		try {
			Optional<Patient> doctoroptional = repo.findById(id);
			if(doctoroptional.isPresent()) {
				repo.deleteById(id);
				return new ResponseEntity<>("Deleted the patient from the database",HttpStatus.OK);
			}
			else
			{
				logger.error("patient not found in the database");
				return new ResponseEntity<>("Patient not found in the database",HttpStatus.NOT_FOUND);
			}

		}
		catch(Exception e) {
			logger.error(e.getMessage());
			return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
		}
	}

	@Override
	@Cacheable(key="#id",value="Patient")
	public Patient getSinglePatient(String id) throws Exception {
		try {
			Optional<Patient> doctoroptional = repo.findById(id);
			if(doctoroptional.isPresent()) {
				System.out.println("Database");
//				return new ResponseEntity<patient>(doctoroptional.get(),HttpStatus.OK);
				return doctoroptional.get();
			}
			else
			{
				logger.error("patient not found in the database");
//				return new ResponseEntity<>("patient not found in the database",HttpStatus.NOT_FOUND);
				throw new Exception("patient not found in the database");
			}
		}
		catch(Exception e) {
			logger.error(e.getMessage());
			throw new Exception(e.getMessage());
		}
	}



	@Override
	public OutputModel getOutput(String id) {
		Patient patient = repo.findById(id).get();
        String uri = "http://localhost:8063/hosp/{hid}";
        Map<String,Integer> uriparam = new HashMap<>();
        uriparam.put("hid", patient.getHid());
        RestTemplate restTemplate = new RestTemplate();
        Hospital res = restTemplate.getForObject(uri,Hospital.class, uriparam );
        logger.info(res.toString());
        OutputModel om = new OutputModel();
        om.setId(patient.getId());
        om.setName(patient.getName());
        om.setAge(patient.getAge());
        om.setBloodgroup(patient.getBloodgroup());
        om.setEmail(patient.getEmail());
        om.setGender(patient.getGender());
        om.setPhone(patient.getPhone());
        om.setHid(patient.getHid());
        om.setHosName(res.getHosName());
        return om;
	}

}
