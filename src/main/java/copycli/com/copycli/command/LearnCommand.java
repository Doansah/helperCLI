package copycli.com.copycli.command;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import copycli.com.copycli.model.EducationPlan;
import copycli.com.copycli.service.LearningService;

@ShellComponent
public class LearnCommand {
    
    @Autowired
    private LearningService learningService;

    // view all topics your learning
    @ShellMethod(value = "view-all-topics", key="view-topics")
    public String viewAllTopics() {

        // learnservice -> find all topics
        List<EducationPlan> educationPlansList = learningService.findAllEducationPlans();
        
        StringBuilder result = new StringBuilder();
        for (EducationPlan eduPlan : educationPlansList) {
            String topic = eduPlan.getTopicDescription();
            String learnplan = eduPlan.getLearningPlan();
            result.append("Topic: ").append(topic).append(" LearningPlan: ").append(learnplan).append(System.lineSeparator());
        }

        return result.toString();

    }


   
}

