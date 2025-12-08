package com.h2lib.aiservice.service.imp;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.h2lib.aiservice.model.Activity;
import com.h2lib.aiservice.model.Recommendation;
import com.h2lib.aiservice.service.ActivityAiService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class ActivityAiServiceImp implements ActivityAiService {

    private final GeminiServiceImp geminiServiceImp;

    public Recommendation geminiRecommendation(Activity activity) {
        String prompt = creatNewPromptForActivity(activity);
        String aiAnswer = geminiServiceImp.getAnswer(prompt);
        log.info("Response from AI = {}", aiAnswer);
        return processAiResponse(activity, aiAnswer);
    }


    public Recommendation processAiResponse(Activity activity, String aiResponse) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(aiResponse);

            JsonNode activityJson = jsonNode
                    .path("candidates")
                    .get(0)
                    .path("content")
                    .path("parts")
                    .get(0)
                    .path("text");

            String jsonContent = activityJson.asText()
                    .replaceAll("```json\\n", "")
                    .replaceAll("\\n```", "").trim();

            log.info("parsed Response form AI = {}", jsonContent);

            JsonNode responseJson = objectMapper.readTree(jsonContent);
            JsonNode analysisNode = responseJson.path("analysis");
            StringBuilder fullAnalysis = new StringBuilder();

            addAnalysisSelection(fullAnalysis, analysisNode, "overall", "Overall:");
            addAnalysisSelection(fullAnalysis, analysisNode, "pace", "Pace:");
            addAnalysisSelection(fullAnalysis, analysisNode, "heartRate", "Heart Rate:");
            addAnalysisSelection(fullAnalysis, analysisNode, "caloriesBurned", "Calories Burned:");

            List<String> improvement = extractImprovement(responseJson.path("improvement"));
            List<String> suggestion = extractSuggestion(responseJson.path("suggestions"));
            List<String> safety = extractSafetyGuidelines(responseJson.path("safety"));

            return Recommendation.builder()
                    .activityId(activity.getId())
                    .userId(activity.getUserId())
                    .activityType(activity.getTypeActivity())
                    .recommendation(fullAnalysis.toString().trim())
                    .improvements(improvement)
                    .suggestions(suggestion)
                    .safety(safety)
                    .createTime(LocalDateTime.now())
                    .build();

        } catch (Exception e) {
            log.error("processAiResponse: ", e);
        }
        return createDefaultRecommendation(activity);
    }

    private Recommendation createDefaultRecommendation(Activity activity) {
        return Recommendation.builder()
                .activityId(activity.getId())
                .userId(activity.getUserId())
                .activityType(activity.getTypeActivity())
                .recommendation("unable to generate default recommendation")
                .improvements(Collections.singletonList("continue with your recommendation"))
                .suggestions(Collections.singletonList("unable to generate default suggestions"))
                .safety(Arrays.asList(
                        "always warm up before exercise",
                        "stay hydrating",
                        "listen to your body"
                ))
                .createTime(LocalDateTime.now())
                .build();
    }

    private List<String> extractSafetyGuidelines(JsonNode safetyNode) {
        List<String> safety = new ArrayList<>();
        if (safetyNode.isArray()) {
            safetyNode.forEach(item -> {
                safety.add(item.asText());
            });
        }
        return safety.isEmpty() ?
                Collections.singletonList("No Specific improvements provide") : safety;
    }

    private List<String> extractSuggestion(JsonNode suggestionNode) {
        List<String> suggestions = new ArrayList<>();
        if (suggestionNode.isArray()) {
            suggestionNode.forEach(node -> {
                String workout = node.path("workout").asText();
                String description = node.path("description").asText();
                suggestions.add(String.format("%s: %s", workout, description));
            });
        }
        return suggestions.isEmpty() ?
                Collections.singletonList("No Specific improvements provide") : suggestions;
    }

    private List<String> extractImprovement(JsonNode improvementNode) {
        List<String> improvements = new ArrayList<>();
        if (improvementNode.isArray()) {
            improvementNode.forEach(node -> {
                String area = node.path("area").asText();
                String detailed = node.path("recommendation").asText();
                improvements.add(String.format("%s: %s", area, detailed));
            });
        }
        return improvements.isEmpty() ?
                Collections.singletonList("No Specific improvements provide") : improvements;
    }

    private void addAnalysisSelection(StringBuilder fullAnalysis, JsonNode analysisNode, String key, String prefix) {
        if (!analysisNode.path(key).isMissingNode()) {
            fullAnalysis.append(key).append(prefix)
                    .append(analysisNode.path(key).asText())
                    .append("\n\n");
        }

    }

    private String creatNewPromptForActivity(Activity activity) {
        return String.format("""
                        Analyze this fitness activity and provide detailed recommendation in the following
                         {
                         "analysis": {
                         "overall": "overall analysis here",
                         "pace": "Pace analysis here",
                         "heartRate": "heart rate analysis here",
                         "caloriesBurned": "calories burned analysis here",
                         },
                         "improvement": [
                             {
                         "area": "area name",
                         "recommendation": "detailed recommendation"
                          }
                         ],
                         "suggestions": [{
                         "workout": "workout name",
                         "description": "detailed workout description"
                         }
                         ],
                         "safety": [
                         "safety point 1",
                         "safety point 2"
                         ]
                         }
                        
                         Analyze this activity:
                         Activity Type: %s
                         Duration Type: %d
                         calories Burned: %d
                         Additional Metrics: %s
                        
                         provide detailed analysis focusing on performance, improvement, next workout suggestions, and safety guidelines
                         Ensure rhe response follows the EXACT JSON format shown above.
                        """,
                activity.getTypeActivity(),
                activity.getDuration(),
                activity.getCaloriesBurned(),
                activity.getAdditionalMatrices()
        );
    }

}
