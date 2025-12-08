package com.h2lib.activity.model.mapper;

import com.h2lib.activity.model.Activity;
import com.h2lib.activity.model.dto.ActivityDto;
import com.h2lib.activity.model.dto.ActivityRequest;

import java.util.HashMap;

public class ActivityMapper {
    public static Activity convertToEntity(ActivityRequest activityRequest){
        Activity activity = new Activity();

        activity.setUserId(activityRequest.getUserId());
        activity.setTypeActivity(activityRequest.getTypeActivity());
        activity.setDuration(activityRequest.getDuration());
        activity.setCaloriesBurned(activityRequest.getCaloriesBurned());
        activity.setTimeStart(activityRequest.getTimeStart());
        if (activityRequest.getAdditionalMatrices() == null ||
                activityRequest.getAdditionalMatrices().isEmpty()) {
            activityRequest.setAdditionalMatrices(new HashMap<>());
        }
        activity.setAdditionalMatrices(activityRequest.getAdditionalMatrices());
        return activity;
    }
    public static ActivityDto convertToDTO(Activity activity){
        ActivityDto activityDto = new ActivityDto();

        activityDto.setId(activity.getId());
        activityDto.setUserId(activity.getUserId());
        activityDto.setTypeActivity(activity.getTypeActivity());
        activityDto.setDuration(activity.getDuration());
        activityDto.setCaloriesBurned(activity.getCaloriesBurned());
        activityDto.setTimeStart(activity.getTimeStart());
        if (activity.getAdditionalMatrices() == null ||
                activity.getAdditionalMatrices().isEmpty()) {
            activity.setAdditionalMatrices(new HashMap<>());
        }
        activityDto.setAdditionalMatrices(activity.getAdditionalMatrices());
        activityDto.setTimeCreated(activity.getTimeCreated());
        activityDto.setTimeUpdated(activity.getTimeUpdated());

        return activityDto;
    }
}
