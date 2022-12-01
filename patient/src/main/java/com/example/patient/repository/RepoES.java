package com.example.patient.repository;

import com.example.patient.model.Patient;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface RepoES extends ElasticsearchRepository<Patient,String> {
}
