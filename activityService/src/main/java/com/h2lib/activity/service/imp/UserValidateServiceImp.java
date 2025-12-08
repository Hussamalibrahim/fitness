package com.h2lib.activity.service.imp;

import com.h2lib.activity.service.UserValidateService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@Slf4j
@Service
@AllArgsConstructor
public class UserValidateServiceImp implements UserValidateService{

    private final WebClient webClient;

    public boolean userValidate(Long userId) {
        try {
            return Boolean.TRUE.equals(webClient.get()
                    .uri("/api/user/{userId}/validate", userId)
                    .retrieve()
                    .bodyToMono(Boolean.class)
                    .block());
        }catch (WebClientResponseException e){
            log.error("Unable to validate user with id: {}", userId, e);
            throw new RuntimeException("user not found: " + userId);
        }catch (Exception e){
            log.error("Unable to validate user with id: {}", userId, e);
            throw new RuntimeException("Bad Request");
        }
    }





}



