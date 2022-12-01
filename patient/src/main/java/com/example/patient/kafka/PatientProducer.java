package com.example.patient.kafka;

import com.example.patient.model.Patient;
import org.apache.kafka.clients.admin.NewTopic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
public class PatientProducer {

    private static final Logger LOGGER = LoggerFactory.getLogger(PatientProducer.class);
    @Autowired
    private NewTopic topic;

    @Autowired
    private KafkaTemplate<String, Patient> kafkaTemplate;

//    public PatientProducer(NewTopic topic, KafkaTemplate<String, OrderEvent> kafkaTemplate) {
//        this.topic = topic;
//        this.kafkaTemplate = kafkaTemplate;
//    }59

    public void sendMessage(Patient event){
        LOGGER.info(String.format("Patient event sent to kafka => %s",event.toString()));
        Message<Patient> message = MessageBuilder
                .withPayload(event)
                .setHeader(KafkaHeaders.TOPIC, topic.name())
                .build();
        kafkaTemplate.send(message);
    }

}
