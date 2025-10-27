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

    public List<EducationPlan> findAllEducationPlans() {
        return learningRepository.findAll();

    }


    
    
}