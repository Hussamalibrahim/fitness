package com.h2lib.activity.controller;


import com.h2lib.activity.model.dto.ActivityDto;
import com.h2lib.activity.model.dto.ActivityRequest;
import com.h2lib.activity.service.ActivityService;
import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import org.checkerframework.common.value.qual.EnsuresMinLenIf;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/activity")
@AllArgsConstructor
public class ActivityController {

    private ActivityService activityService;

    @PostMapping("add")
    public ResponseEntity<ActivityDto> addActivity(@RequestBody ActivityRequest activityRequest){
        return ResponseEntity.ok(activityService.addActivity(activityRequest));
    }

    @GetMapping("all")
    public ResponseEntity<List<ActivityDto>> getAllActivities(){
        return ResponseEntity.ok(activityService.getActivities());
    }

    @GetMapping
    public ResponseEntity<List<ActivityDto>> getUserActivities(@RequestHeader("X-USER-ID") Long userId){
        return ResponseEntity.ok(activityService.getUserActivities(userId));
    }
    @GetMapping("{activityId}")
    public ResponseEntity<ActivityDto> getActivity(@Nullable @PathVariable String activityId){
        return ResponseEntity.ok(activityService.getActivity(activityId));
    }


}
