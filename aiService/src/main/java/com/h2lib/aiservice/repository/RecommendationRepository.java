package com.h2lib.aiservice.repository;

import com.h2lib.aiservice.model.Recommendation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Repository
public interface RecommendationRepository extends MongoRepository<Recommendation, String> {
    List<Recommendation> findByUserId(Long userId);

    Optional<Recommendation> findByActivityId(String activityId);
}
