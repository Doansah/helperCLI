package copycli.com.copycli.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import copycli.com.copycli.model.EducationPlan;
import copycli.com.copycli.repository.LearningRepository;

@Service
public class LearningService {
    
    @Autowired
    private LearningRepository learningRepository;

    public EducationPlan createLearningPlan(String topic) {
        // Create a basic learning plan (you can enhance this later with AI/external APIs)
        String learningPlan = generateBasicLearningPlan(topic);
        
        EducationPlan plan = new EducationPlan(topic, learningPlan);
        return learningRepository.save(plan);
    }
    
    public String getAllPlans() {
        List<EducationPlan> plans = learningRepository.findAll();
        
        if (plans.isEmpty()) {
            return "📚 No learning plans found. Create one with 'learning-objectives [topic]'";
        }
        
        StringBuilder result = new StringBuilder("📚 Your Learning Plans:\n\n");
        for (int i = 0; i < plans.size(); i++) {
            EducationPlan plan = plans.get(i);
            result.append((i + 1)).append(". Topic: ").append(plan.getTopicDescription())
                  .append("\n   Plan: ").append(plan.getLearningPlan())
                  .append("\n\n");
        }
        
        return result.toString();
    }
    
    private String generateBasicLearningPlan(String topic) {
        // Basic template - you can enhance this with AI later
        return String.format(
            "Learning objectives for '%s':\n" +
            "1. Understand the fundamentals of %s\n" +
            "2. Learn key concepts and terminology\n" +
            "3. Practice with hands-on examples\n" +
            "4. Apply knowledge in real scenarios\n" +
            "5. Review and assess understanding",
            topic, topic
        );
    }
}