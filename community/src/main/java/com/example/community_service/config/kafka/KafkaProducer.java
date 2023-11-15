package com.example.community_service.config.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaProducer {
    private final KafkaTemplate<String, String> kafkaTemplate;

    // "test, message"로 test
    public void sendMessage(String kafkaTopic, String message){
        ObjectMapper mapper = new ObjectMapper();
        System.out.println("kafka message send in 1:" + message);
        String jsonInString = "";
        try {
            jsonInString = mapper.writeValueAsString(message);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        System.out.println("kafka message send in 2:" + jsonInString);
        try{
            System.out.println("kafka message send in 3:" + jsonInString);
            kafkaTemplate.send(kafkaTopic, message);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
//    public void sendMessage : kafkaProducer.send("topicName", messageDto);
}
