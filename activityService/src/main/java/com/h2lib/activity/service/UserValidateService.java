package com.h2lib.activity.service;

public interface UserValidateService {
    boolean userValidate(String keycloakId) ;

    Long getUserIdByKeycloakId(String keycloakId);
}
