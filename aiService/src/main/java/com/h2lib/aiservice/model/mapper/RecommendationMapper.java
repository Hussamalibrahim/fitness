package com.h2lib.aiservice.model.mapper;


import com.h2lib.aiservice.model.Recommendation;
import com.h2lib.aiservice.model.dto.RecommendationDto;

public class RecommendationMapper {

//    public static Recommendation convertToEntity(RecommendationDto recommendationDto) {
//        Recommendation recommendation = new Recommendation();
//
//        recommendation.setUserId(recommendationDto.getUserId());
//        recommendation.setActivityId(recommendationDto.getActivityId());
//        recommendation.setActivityType(recommendationDto.getActivityType());
//        recommendation.setRecommendation(recommendationDto.getRecommendation());
//        recommendation.setImprovements(recommendationDto.getImprovements());
//        recommendation.setSuggestions(recommendationDto.getSuggestions());
//        recommendation.setSafety(recommendationDto.getSafety());
//
//        return recommendation;
//    }

    public static RecommendationDto convertToDto(Recommendation recommendation) {
        RecommendationDto recommendationDto = new RecommendationDto();

        recommendationDto.setId(recommendation.getId());
        recommendationDto.setUserId(recommendation.getUserId());
        recommendationDto.setActivityId(recommendation.getActivityId());
        recommendationDto.setActivityType(recommendation.getActivityType());
        recommendationDto.setRecommendation(recommendation.getRecommendation());
        recommendationDto.setImprovements(recommendation.getImprovements());
        recommendationDto.setSafety(recommendation.getSafety());
        recommendationDto.setCreateTime(recommendation.getCreateTime());

        return recommendationDto;
    }
}
