package com.example.patient.repository;

import com.example.patient.model.Patient;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface Repo extends MongoRepository<Patient,String>{  //model class name,data type of id

	
	@Query("{'bloodgroup': ?0}")
	List<Patient> findByBloodgroup(String bloodgroup);
	
	@Query("{'age': ?0}")
	List<Patient> findByAge(int age);
}

//blue is the main query
//this where the function gets called and it is predefined