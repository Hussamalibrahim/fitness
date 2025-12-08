package com.h2lib.aiservice.controller;

import com.h2lib.aiservice.model.Recommendation;
import com.h2lib.aiservice.model.dto.RecommendationDto;
import com.h2lib.aiservice.service.RecommendationService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/recommendation")
@AllArgsConstructor
@SuppressWarnings({"JvmTaintAnalysis"})
public class RecommendationController {
    private final RecommendationService recommendationService;

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<RecommendationDto>> getRecommendationByUserId(@PathVariable("userId") Long userId) {
        return ResponseEntity.ok().body(recommendationService.getRecommendationByUserId(userId));
    }
    @GetMapping("/activity/{activityId}")
    public ResponseEntity<RecommendationDto> getRecommendationByActivityId(@PathVariable("activityId") String activityId) {
        return ResponseEntity.ok().body(recommendationService.getRecommendationByActivityId(activityId));
    }

    @GetMapping("/all")
    public ResponseEntity<List<RecommendationDto>> getAllRecommendation() {
        return ResponseEntity.ok().body(recommendationService.getAllRecommendation());
    }

}
