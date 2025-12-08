package com.h2lib.activity.service;

import com.h2lib.activity.model.dto.ActivityDto;
import com.h2lib.activity.model.dto.ActivityRequest;

import java.util.List;

public interface ActivityService {
    ActivityDto addActivity(ActivityRequest activityRequest);

    List<ActivityDto> getActivities();

    List<ActivityDto> getUserActivities(Long userId);

    ActivityDto getActivity(String activityId);
}
