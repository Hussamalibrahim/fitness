package com.h2lib.activity.repository;

import com.h2lib.activity.model.Activity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActivityRepository extends MongoRepository<Activity, String> {
    List<Activity> findActivitiesByUserId(Long userId);

}
