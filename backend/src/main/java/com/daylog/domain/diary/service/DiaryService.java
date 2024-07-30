package com.daylog.domain.diary.service;

import com.daylog.domain.diary.dto.DiaryResponse;
import com.daylog.domain.diary.repository.DiaryRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class DiaryService {

    private final DiaryRepository diaryRepository;
    private static final String DALL_E_API_URL = "https://api.openai.com/v1/images/generations";
    private static final String API_KEY = "sk-proj-ZeOmBbYpL8qA6HjURVIyT3BlbkFJ5mArUuWxqQymBfoXkiYU";

    public DiaryResponse generateDiaryImage(String text) {
        RestTemplate restTemplate = new RestTemplate();

        // 프롬프트 설정
        String prompt = String.format(
//                "Create an illustration with soft facial features and captivating eyes that draw in the viewer. " +
//                        "The contrast between the fantastical characters and the more traditional color schemes and elements " +
//                        "provides an interesting narrative quality to the work. The style should incorporate painting realism, " +
//                        "photorealism, and fantasy digital art, based on the following text: \"%s\".",
                text
        );

        // 요청 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(API_KEY);

        // 요청 본문 설정
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode requestBody = mapper.createObjectNode();
        requestBody.put("prompt", prompt);
        requestBody.put("n", 1);  // 생성할 이미지 수
        requestBody.put("size", "1024x1024");  // 이미지 크기
        requestBody.put("model", "dall-e-3");  // DALL-E 3 모델 사용

        HttpEntity<String> entity;
        try {
            entity = new HttpEntity<>(mapper.writeValueAsString(requestBody), headers);
        } catch (Exception e) {
            throw new RuntimeException("Failed to create request body", e);
        }

        // DALL·E API 호출
        ResponseEntity<String> response = restTemplate.exchange(DALL_E_API_URL, HttpMethod.POST, entity, String.class);

        // 응답 처리
        if (response.getStatusCode() == HttpStatus.OK) {
            try {
                ObjectNode responseJson = mapper.readValue(response.getBody(), ObjectNode.class);
                String imageUrl = responseJson.get("data").get(0).get("url").asText();
                return new DiaryResponse(imageUrl);
            } catch (Exception e) {
                throw new RuntimeException("Failed to parse response", e);
            }
        } else {
            throw new RuntimeException("Failed to generate image: " + response.getStatusCode());
        }
    }
}
