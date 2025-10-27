package copycli.com.copycli.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class EducationPlan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String topicDescription;
    private String learningPlan;

    // Default constructor
    public EducationPlan() {}

    // Constructor with parameters
    public EducationPlan(String topicDescription, String learningPlan) {
        this.topicDescription = topicDescription;
        this.learningPlan = learningPlan;
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTopicDescription() {
        return topicDescription;
    }

    public void setTopicDescription(String topicDescription) {
        this.topicDescription = topicDescription;
    }

    public String getLearningPlan() {
        return learningPlan;
    }

    public void setLearningPlan(String learningPlan) {
        this.learningPlan = learningPlan;
    }
}