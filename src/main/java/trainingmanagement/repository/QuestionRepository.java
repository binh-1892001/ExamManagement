package trainingmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import trainingmanagement.model.entity.Question;
import trainingmanagement.model.entity.Test;
import trainingmanagement.model.enums.EQuestionLevel;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
    List<Question> getAllByTest(Test test);
    List<Question> getAllByCreatedDate(LocalDate date);
    @Query(value = "select * from question where created_date between :dateStart and :dateEnd",nativeQuery = true)
    List<Question> getAllFromDateToDate(LocalDate dateStart,LocalDate dateEnd);
    List<Question> findAllByQuestionContentIsContainingIgnoreCase(String questionContent);
    List<Question> getAllByQuestionLevel(EQuestionLevel questionLevel);
    List<Question> getQuestionsByTestId(Long testId);

    
}
