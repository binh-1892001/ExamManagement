package trainingmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import trainingmanagement.model.entity.Question;
import trainingmanagement.model.entity.Test;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
    List<Question> findAllByContentQuestionIsContainingIgnoreCase(String contentQuestion);
    List<Question> getAllByTest(Test test);
}
