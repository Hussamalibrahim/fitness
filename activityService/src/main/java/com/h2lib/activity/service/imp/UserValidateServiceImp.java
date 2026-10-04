package com.h2lib.activity.service.imp;

import com.h2lib.activity.service.UserValidateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserValidateServiceImp implements UserValidateService{

    @Value("${gateway.internal.key}")
    private String gatewayKey;

    private final WebClient webClient;

    public boolean userValidate(String keycloakId) {
        try {
            return Boolean.TRUE.equals(webClient.get()
                    .uri("/api/user/{keycloakId}/validate", keycloakId)
                    .retrieve()
                    .bodyToMono(Boolean.class)
                    .block());
        }catch (WebClientResponseException e){
            log.error("Unable to validate user with id: {}", keycloakId, e);
            throw new RuntimeException("user not found: " + keycloakId);
        }catch (Exception e){
            log.error("Unable to validate user with id: {}", keycloakId, e);
            throw new RuntimeException("Bad Request");
        }
    }

    @Override
    public Long getUserIdByKeycloakId(String keycloakId) {
        try {
            return webClient.get()
                    .uri("/api/user/internal/id")
                    .header("X-KEYCLOAK-ID", keycloakId)
                    .header("X-Gateway-Key", gatewayKey)
                    .retrieve()
                    .bodyToMono(Long.class)
                    .block();

        } catch (WebClientResponseException e) {
            log.error("Unable to get user by Keycloak ID: {}", keycloakId, e);
            throw new RuntimeException("User not found");
        } catch (Exception e) {
            log.error("Unable to communicate with User Service", e);
            throw new RuntimeException("User Service unavailable");
        }
    }
}



