package com.h2lib.activity.model.dto;

import com.h2lib.activity.model.enumeration.TypeActivity;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Data
public class ActivityDto {
    private String id;
    private Long userId;
    private TypeActivity typeActivity;
    private Integer duration;
    private Integer caloriesBurned;
    private LocalDateTime timeStart;
    private Map<String, Object> additionalMatrices = new HashMap<>();
    private LocalDateTime timeCreated;
    private LocalDateTime timeUpdated;

}
