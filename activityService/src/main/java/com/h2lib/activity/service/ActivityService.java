package com.h2lib.activity.service;

import com.h2lib.activity.model.dto.ActivityDto;
import com.h2lib.activity.model.dto.ActivityRequest;

import java.util.List;

public interface ActivityService {
    ActivityDto addActivity(ActivityRequest activityRequest, String keycloakId);

    List<ActivityDto> getActivities();

    List<ActivityDto> getUserActivities(String userId);

    ActivityDto getActivity(String activityId);
}
