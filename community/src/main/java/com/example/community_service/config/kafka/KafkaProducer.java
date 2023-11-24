
package com.example.community_service.config.kafka;

import com.example.community_service.config.kafka.dto.CommunityInteractionDto;
import com.example.community_service.config.kafka.dto.CreatorDataAggregationDto;
import com.example.community_service.config.kafka.dto.InteractionType;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaProducer {
    private final KafkaTemplate<String, String> kafkaTemplate;
    // env에 등록된 kafka topic을 가져옴
    @Value("${spring.kafka.topic2.name}")
    private String topic2;
    @Value("${spring.kafka.topic3.name}")
    private String topic3;
    // "test, message"로 test
    public void sendMessage(String kafkaTopic, String message){
        System.out.println("kafka message send in 2:" + message);
        try{
            kafkaTemplate.send(kafkaTopic, message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void producerJoinCommunity(Long communityId, Long point) throws JsonProcessingException {
        CommunityInteractionDto communityInteractionDto
                = new CommunityInteractionDto(communityId, point, InteractionType.JOIN);
        // communityInteractionDto를 json으로 변환
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonInString = objectMapper.writeValueAsString(communityInteractionDto);
        sendMessage(topic2,jsonInString);
    }
    public void producerCreateCreator(Long communityId ,String userUuid, String communityName, String youtubeName)
            throws JsonProcessingException {
        CreatorDataAggregationDto creatorDataAggregationDto
                = new CreatorDataAggregationDto(communityId, userUuid, communityName, youtubeName);
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonInString = objectMapper.writeValueAsString(creatorDataAggregationDto);
        sendMessage(topic3,jsonInString);
    }
}
