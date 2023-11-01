package com.example.community_service.community.application;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.HashMap;

@Service
@Slf4j
@ToString
@RequiredArgsConstructor
public class YoutubeService {

    private final WebClient webClient;

    public HashMap<String, String> getMyChannelInfo(String token) throws JsonProcessingException {

        String partValues = "brandingSettings,snippet";
        String response = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/channels")
                        .queryParam(
                                "part", partValues)
                        .queryParam("mine", true)
                        .build()
                )
                .headers(h -> h.setBearerAuth(token))
                .retrieve()
                .bodyToMono(String.class)
                .block();

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(response);
        HashMap<String ,String> responseData = new HashMap<>();

        responseData.put("profileImageUrl",
                jsonNode.get("items").get(0).get("snippet").get("thumbnails").get("high").get("url").asText());
        responseData.put("bannerImageUrl",
                jsonNode.get("items").get(0).get("brandingSettings").get("image").get("bannerExternalUrl").asText());
        responseData.put("youtubeName",
                jsonNode.get("items").get(0).get("snippet").get("title").asText());

        return responseData;
    }

    // todo: 일단 사용하지 않는 것으로 가정하고 주석처리
//    // 크리에이터 확인 외부 API(user 서비스)
//    public Boolean checkCreator(String uuid) throws JsonProcessingException {
//
//        String response = webClient.post()
//                .uri(uriBuilder -> uriBuilder
//                        .path("/users/is-creator")
//                        .build()
//                )
//                // 인라인 폼 데이터 생성
//                .body(fromFormData("uuid", uuid))
//                .retrieve()
//                .bodyToMono(String.class)
//                .block();
//
//        ObjectMapper objectMapper = new ObjectMapper();
//        JsonNode jsonNode = objectMapper.readTree(response);
//
//        // response의 data값 파싱
//        String isCreator = jsonNode.get("data").get("isCreator").asText();
//        return Boolean.parseBoolean(isCreator);
//    }
}
