package com.h2lib.aiservice.service.imp;

import com.h2lib.aiservice.config.GeminiProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

@Service
public class GeminiServiceImp {

    private final WebClient webClient;

    @Autowired
    private GeminiProperties geminiProperties;

    public GeminiServiceImp(WebClient.Builder webClient) {
        this.webClient = webClient.build();
    }

    public String getAnswer(String question) {
        Map<String, Object> requestBody = Map.of(
                "contents", new Object[]{
                        Map.of("parts", new Object[]{
                                Map.of("text", question)
                        })
                }
        );
        return webClient.post()
                .uri(geminiProperties.getGeminiApiUrl() + geminiProperties.getGeminiApiKey())
                .header("Content-Type", "application/json")
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
}
