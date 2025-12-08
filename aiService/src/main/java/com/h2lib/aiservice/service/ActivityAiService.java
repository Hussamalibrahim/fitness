package com.h2lib.aiservice.service;

import com.h2lib.aiservice.model.Activity;
import com.h2lib.aiservice.model.Recommendation;

public interface ActivityAiService {
     Recommendation geminiRecommendation(Activity activity);

}
