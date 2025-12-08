package com.h2lib.activity.model;


import com.h2lib.activity.model.enumeration.TypeActivity;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Data
@Document(collection = "activities")
public class Activity {
    @Id
    private String id;
    @Field(name = "user_id")
    private Long userId;
    @Field(name = "type_activity")
    private TypeActivity typeActivity;
    @Field(name = "duration")
    private Integer duration;
    @Field(name = "calories_burned")
    private Integer caloriesBurned;
    @Field(name = "time_start")
    private LocalDateTime timeStart;
    @Field(name = "additional_matrices")
    private Map<String, Object> additionalMatrices = new HashMap<>();
    @CreatedDate
    @Field(name = "time_created")
    private LocalDateTime timeCreated;
    @LastModifiedDate
    @Field(name = "time_updated")
    private LocalDateTime timeUpdated;




}
