
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
        System.out.println("kafka message send in 2:" + message);
        try{
            kafkaTemplate.send(kafkaTopic, message);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
//    public void sendMessage : kafkaProducer.send("topicName", messageDto);
}
