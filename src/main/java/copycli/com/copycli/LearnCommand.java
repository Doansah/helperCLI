package copycli.com.copycli;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import copycli.com.copycli.model.EducationPlan;
import copycli.com.copycli.service.LearningService;

@ShellComponent
public class LearnCommand {
    
    @Autowired
    private LearningService learningService;

    @ShellMethod(value = "Create learning objectives for a topic", key = "learning-objectives")
    public String createLearningObjectives(String topic) {
        try {
            EducationPlan plan = learningService.createLearningPlan(topic);
            return "📚 Learning plan created for: " + topic + "\n" + 
                   "Plan: " + plan.getLearningPlan();
        } catch (Exception e) {
            return "❌ Error creating learning objectives: " + e.getMessage();
        }
    }
    
    @ShellMethod(value = "List all saved learning plans", key = "list-plans")
    public String listLearningPlans() {
        try {
            return learningService.getAllPlans();
        } catch (Exception e) {
            return "❌ Error retrieving learning plans: " + e.getMessage();
        }
    }
}

