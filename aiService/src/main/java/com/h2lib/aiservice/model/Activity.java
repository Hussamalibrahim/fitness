package com.h2lib.aiservice.model;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Data
public class Activity {
    @Id
    private String id;
    private Long userId;
    private String typeActivity;
    private Integer duration;
    private Integer caloriesBurned;
    private LocalDateTime timeStart;
    private Map<String, Object> additionalMatrices = new HashMap<>();
    private LocalDateTime timeCreated;
    private LocalDateTime timeUpdated;
}
