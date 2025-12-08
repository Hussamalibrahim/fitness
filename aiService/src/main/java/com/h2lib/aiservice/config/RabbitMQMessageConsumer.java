package com.h2lib.aiservice.config;

import com.h2lib.aiservice.model.Activity;
import com.h2lib.aiservice.model.Recommendation;
import com.h2lib.aiservice.service.ActivityAiService;
import com.h2lib.aiservice.service.RecommendationService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@AllArgsConstructor
public class RabbitMQMessageConsumer {

    private final ActivityAiService activityAiService;
    private final RecommendationService recommendationService;

    @RabbitListener(queues = "${rabbit.mq.queue.message}")
    public void receiveMessageJson(Activity activity) {
        try {
            log.info("Received activity message: {}", activity.toString());

            if (activity.getUserId() == null) {
                log.error("Invalid activity received: {}", activity);
                return;
            }

            Recommendation recommendation = activityAiService.geminiRecommendation(activity);
            Recommendation savedRecommendation = recommendationService.save(recommendation);

            log.info("Successfully processed activity and saved recommendation: {}", savedRecommendation.getId());

        } catch (Exception e) {
            log.error("Error processing activity message: {}", activity, e);
            throw new AmqpRejectAndDontRequeueException("Failed to process message", e);
        }
    }
}