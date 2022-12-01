package com.example.emailservice.kafka;

import com.example.patient.model.Patient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class PatientConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(PatientConsumer.class);

    @KafkaListener(
            topics = "${spring.kafka.topic.name}",
            groupId= "${spring.kafka.consumer.group-id}"
    )
    public void consume(Patient event){
        LOGGER.info(String.format("Patient Registered !! It is received in the email service => %s",event.toString()));
    }
    // add here to save the data to a database
}
