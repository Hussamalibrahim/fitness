package com.h2lib.aiservice.model.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;


@Data
public class RecommendationDto {
    private String id;
    private String activityId;
    private Long userId;
    private String activityType;
    private String recommendation;
    private List<String> improvements;
    private List<String> suggestions;
    private List<String> safety;
    private LocalDateTime createTime;
}
