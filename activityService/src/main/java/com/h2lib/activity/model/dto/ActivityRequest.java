package com.h2lib.activity.model.dto;

import com.h2lib.activity.model.enumeration.TypeActivity;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Data
public class ActivityRequest {
    private TypeActivity typeActivity;
    private Integer duration;
    private Integer caloriesBurned;
    private LocalDateTime timeStart;
    private Map<String, Object> additionalMatrices = new HashMap<>();
}
