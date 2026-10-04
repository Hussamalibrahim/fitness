package com.h2lib.aiservice.model;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@Document(collection = "Recommendation")
public class Recommendation {

    @Id
    private String id;

    @Field(name = "activity_id")
    private String activityId;

    @Field(name = "user_id")
    private Long userId;

    @Field(name = "activity_type")
    private String activityType;

    @Field(name = "recommendation")
    private String recommendation;

    @Field(name = "improvements")
    private List<String> improvements;

    @Field(name = "suggestions")
    private List<String> suggestions;

    @Field(name = "safety")
    private List<String> safety;

    @CreatedDate
    @Field(name = "create-time")
    private LocalDateTime createTime;

}
