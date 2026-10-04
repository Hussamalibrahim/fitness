package com.h2lib.gateway.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class GatewayKeyFilter implements GlobalFilter, Ordered {

    @Value("${gateway.internal.key}")
    private String gatewayKey;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        return exchange.getPrincipal()
                .cast(Authentication.class)
                .flatMap(authentication -> {

                    ServerHttpRequest request = exchange.getRequest()
                            .mutate()
                            .headers(headers -> {
                                headers.remove("X-Gateway-Key");
                                headers.add("X-Gateway-Key", gatewayKey);

                                if (authentication instanceof JwtAuthenticationToken jwtAuthentication) {
                                    String keycloakId = jwtAuthentication
                                            .getToken()
                                            .getSubject();
                                    log.info("Keycloak ID = {}", keycloakId);

                                    headers.remove("X-KEYCLOAK-ID");
                                    headers.add("X-KEYCLOAK-ID", keycloakId);}})
                            .build();

                    return chain.filter(exchange.mutate().request(request).build());
                })
                .switchIfEmpty(
                        chain.filter(
                                exchange.mutate()
                                        .request(
                                                exchange.getRequest()
                                                        .mutate()
                                                        .headers(headers -> {
                                                            headers.remove("X-Gateway-Key");
                                                            headers.add("X-Gateway-Key", gatewayKey);})
                                                        .build()
                                        ).build())
                );
    }

    @Override
    public int getOrder() {
        return 0;
    }
}