package copycli.com.copycli.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import copycli.com.copycli.model.EducationPlan;

@Repository
public interface LearningRepository extends JpaRepository<EducationPlan, Long> {

}
