package com.h2lib.activity.service.imp;

import com.h2lib.activity.config.RabbitMQActivityProducer;
import com.h2lib.activity.model.Activity;
import com.h2lib.activity.model.dto.ActivityDto;
import com.h2lib.activity.model.dto.ActivityRequest;
import com.h2lib.activity.model.mapper.ActivityMapper;
import com.h2lib.activity.repository.ActivityRepository;
import com.h2lib.activity.service.ActivityService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

import static com.h2lib.activity.model.mapper.ActivityMapper.*;

@Service
@AllArgsConstructor
public class ActivityServiceImp implements ActivityService {
    private static final Logger log = LoggerFactory.getLogger(ActivityServiceImp.class);
    private final ActivityRepository activityRepository;
    private final UserValidateServiceImp userValidateServiceImp;
    RabbitMQActivityProducer rabbitMQActivityProducer;

    @Override
    public ActivityDto addActivity(ActivityRequest activityRequest, String keycloakId) {

        if (activityRequest.getAdditionalMatrices() == null ||
                activityRequest.getAdditionalMatrices().isEmpty()) {
            activityRequest.setAdditionalMatrices(new HashMap<>());
        }

        Long userId = userValidateServiceImp.getUserIdByKeycloakId(keycloakId);
        Activity activity = new Activity();
        activity.setUserId(userId);
        activity.setTypeActivity(activityRequest.getTypeActivity());
        activity.setDuration(activityRequest.getDuration());
        activity.setCaloriesBurned(activityRequest.getCaloriesBurned());
        activity.setTimeStart(LocalDateTime.now());
        activity.setAdditionalMatrices(activityRequest.getAdditionalMatrices());

        Activity dBActivity = activityRepository.save(activity);
        try {
            rabbitMQActivityProducer.sendMessage(dBActivity);
        } catch (Exception e) {log.error("send message failed with rabbitMQ: {}", userId, e);
        }

        log.info("Activity has been saved successfully for user: {}", userId);
        return convertToDTO(dBActivity);
    }

    @Override
    public List<ActivityDto> getActivities() {
        log.info("Getting Activities have been save successfully:");
        return activityRepository.findAll().stream()
                .map(ActivityMapper::convertToDTO)
                .toList();
    }

    @Override
    public List<ActivityDto> getUserActivities(String keycloakId) {
        Long userId = userValidateServiceImp.getUserIdByKeycloakId(keycloakId);
        boolean isUserValid = userValidateServiceImp.userValidate(keycloakId);
        if (!isUserValid) {
            log.error("Unable to find user with id: {}", userId);
            throw new RuntimeException("user validation failed: " + userId);
        }
        log.info("Getting Activities by user id have been save successfully: {}",  userId);
        return activityRepository.findActivitiesByUserId(userId).stream()
                .map(ActivityMapper::convertToDTO)
                .toList();
    }

    @Override
    public ActivityDto getActivity(String activityId) {
        log.info("Getting Activities by activity id have been save successfully: {}",  activityId);
        return activityRepository.findById(activityId)
                .map(ActivityMapper::convertToDTO)
                .orElseThrow(() -> {
                    log.info("Unable to find activity with id: {}", activityId);
                    return new RuntimeException("Unable to find activity with id: " + activityId);
                });
    }

}
