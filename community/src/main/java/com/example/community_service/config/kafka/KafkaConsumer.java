package com.example.community_service.config.kafka;

import com.example.community_service.community.application.CommunityMemberService;
import com.example.community_service.community.application.CommunityService;
import com.example.community_service.community.domain.CommunityMember;
import com.example.community_service.config.kafka.dto.BoardDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaConsumer {
    private final CommunityMemberService communityMemberService;
    private final CommunityService communityService;
    private final KafkaProducer kafkaProducer;
    private final ObjectMapper objectMapper = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    @Value("${spring.kafka.topic1.name}")
    private String boardCreateAlarmTopic;

    @KafkaListener(topics = "test", groupId = "community-service-test")
    public void processMessage(String kafkaMessage) {
        Map<Object, Object> map = new HashMap<>(); // kafka 역직렬화
        try {
            map = objectMapper.readValue(kafkaMessage, new TypeReference<Map<Object, Object>>() {
            });
//            map = objectMapper.readValue(kafkaMessage, class 명);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
    // board데이터와 communityMember데이터를 합쳐서 etc 알람으로 보내는 로직
    @KafkaListener(topics = "boardCreate", groupId = "community-service-boardCreate")
    public void consumeBoardCreate(String message) {
        log.info("kafka message received =====> " + message);
        try {
            BoardDto boardDto = objectMapper.readValue(message, BoardDto.class);
            Map<String, Object> creatorMap = createCreatorMap(boardDto);

            String jsonInString = objectMapper.writeValueAsString(creatorMap);
            kafkaProducer.sendMessage(boardCreateAlarmTopic, jsonInString);
        } catch (Exception e) {
            log.error("Error processing message: ", e);
        }
    }
    // communityId를 통해 커뮤니티 가입 유저 list를 가져오는 로직
    private Map<String, Object> createCreatorMap(BoardDto boardDto) {
        String authorUuid = communityService.getOwnerUuidByCommunityId(boardDto.getCommunityId());
        List<String> members = communityMemberService
                .getUserUuidListByCommunityId(boardDto.getCommunityId(), boardDto.getBoardType());

        Map<String, Object> creatorMap = objectMapper.convertValue(boardDto, new TypeReference<Map<String, Object>>() {});
        creatorMap.put("board", boardDto);
        creatorMap.put("authorUuid", authorUuid);
        creatorMap.put("members", members);
        return creatorMap;
    }
}
