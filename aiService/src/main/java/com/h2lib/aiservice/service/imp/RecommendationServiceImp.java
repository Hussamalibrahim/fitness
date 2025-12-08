package com.h2lib.aiservice.service.imp;

import com.h2lib.aiservice.model.Recommendation;
import com.h2lib.aiservice.model.dto.RecommendationDto;
import com.h2lib.aiservice.model.mapper.RecommendationMapper;
import com.h2lib.aiservice.repository.RecommendationRepository;
import com.h2lib.aiservice.service.RecommendationService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class RecommendationServiceImp implements RecommendationService {

    private final RecommendationRepository recommendationRepository;

    @Override
    public List<RecommendationDto> getRecommendationByUserId(Long userId) {
        return recommendationRepository.findByUserId(userId).stream()
                .map(RecommendationMapper::convertToDto)
                .toList();
    }

    @Override
    public RecommendationDto getRecommendationByActivityId(String activityId) {

        return RecommendationMapper.convertToDto(recommendationRepository.findByActivityId(activityId)
                .orElseThrow(() -> new RuntimeException("activityId not found: " + activityId)));
    }
    @Override
    public List<RecommendationDto> getAllRecommendation(){
        return recommendationRepository.findAll().stream()
                .map(RecommendationMapper::convertToDto)
                .toList();
    }

    @Override
    public Recommendation save(Recommendation recommendation) {
        return recommendationRepository.save(recommendation);
    }
}
