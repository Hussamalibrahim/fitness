package com.h2lib.aiservice.service;

import com.h2lib.aiservice.model.Recommendation;
import com.h2lib.aiservice.model.dto.RecommendationDto;

import java.util.List;

public interface RecommendationService {
    List<RecommendationDto> getRecommendationByUserId(Long userId);

    RecommendationDto getRecommendationByActivityId(String activityId);

    List<RecommendationDto> getAllRecommendation();

    Recommendation save(Recommendation recommendation);
}
