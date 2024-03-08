package trainingmanagement.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import trainingmanagement.model.entity.Question;
@Repository
public interface QuestionRepository extends JpaRepository<Question,Long> {
    Page<Question> findAllByContentQuestionIsContainingIgnoreCase(Pageable pageable, String contentQuestion);
}
