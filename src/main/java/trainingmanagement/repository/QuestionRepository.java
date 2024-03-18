package trainingmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import trainingmanagement.model.entity.Question;
import trainingmanagement.model.entity.Test;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
    List<Question> getAllByTest(Test test);
//    @Query(value = "select * from question where created_date = :date",nativeQuery = true)
    List<Question> getAllByCreatedDate(LocalDate date);
    @Query(value = "select * from question where created_date between :dateStart and :dateEnd",nativeQuery = true)
    List<Question> getAllFromDayToDay(String dateStart,String dateEnd);
    List<Question> findAllByQuestionContentIsContainingIgnoreCase(String questionContent);
}
