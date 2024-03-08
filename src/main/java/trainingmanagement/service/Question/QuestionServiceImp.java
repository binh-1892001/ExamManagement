package trainingmanagement.service.Question;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import trainingmanagement.model.dto.requestEntity.RequestQuestionPostQuiz;
import trainingmanagement.model.dto.requestEntity.RequestQuestionPutQuiz;
import trainingmanagement.model.dto.responseEntity.ResponseQuestionQuiz;
import trainingmanagement.model.entity.Enum.ELevelQuestion;
import trainingmanagement.model.entity.Enum.ETypeQuestion;
import trainingmanagement.model.entity.Option;
import trainingmanagement.model.entity.Question;
import trainingmanagement.repository.QuestionRepository;

@Repository
public class QuestionServiceImp implements QuestionService{
    @Autowired
    private QuestionRepository questionRepository;
    @Override
    public Page<ResponseQuestionQuiz> getAll(Pageable pageable) {
        Page<Question> questions = questionRepository.findAll(pageable);
        return questions.map(this::displayQuestion);
    }

    @Override
    public Question getById(Long id) {
        return questionRepository.findById(id).orElse(null);
    }

    @Override
    public Page<ResponseQuestionQuiz> findByName(String keyWord, Pageable pageable) {
        Page<Question> questions = questionRepository.findAllByContentQuestionIsContainingIgnoreCase(pageable,keyWord);
        return questions.map(this::displayQuestion);
    }


    @Override
    public Question save(Question question) {
        return questionRepository.save(question);
    }

    @Override
    public void deleteById(Long id) {
        questionRepository.deleteById(id);
    }

    @Override
    public ResponseQuestionQuiz displayQuestion(Question question) {
        return ResponseQuestionQuiz.builder()
                .contentQuestion(question.getContentQuestion())
                .levelQuestion(question.getLevelQuestion())
                .typeQuestion(question.getTypeQuestion())
                .status(question.getStatus())
                .build();
    }

    @Override
    public Question addQuestion(RequestQuestionPostQuiz questionQuiz) {
        Question question = new Question();
        question.setContentQuestion(questionQuiz.getContentQuestion());
        question.setLevelQuestion(ELevelQuestion.valueOf(questionQuiz.getLevelQuestion()));
        question.setTypeQuestion(ETypeQuestion.valueOf(questionQuiz.getTypeQuestion()));
        question.setImage(questionQuiz.getImage());
        question.setStatus(true);
        return questionRepository.save(question);
    }

    @Override
    public Question updateQuestion(RequestQuestionPutQuiz requestQuestionPutQuiz, Long idQuestion) {
        Question question = getById(idQuestion);
        question.setContentQuestion(requestQuestionPutQuiz.getContentQuestion());
        question.setTypeQuestion(ETypeQuestion.valueOf(requestQuestionPutQuiz.getTypeQuestion()));
        question.setImage(requestQuestionPutQuiz.getImage());
        question.setLevelQuestion(ELevelQuestion.valueOf(requestQuestionPutQuiz.getLevelQuestion()));
//        question.builder()
//                .contentQuestion(requestQuestionPutQuiz.getContentQuestion())
//                .typeQuestion(ETypeQuestion.valueOf(requestQuestionPutQuiz.getTypeQuestion()))
//                .image(requestQuestionPutQuiz.getImage())
//                .levelQuestion(ELevelQuestion.valueOf(requestQuestionPutQuiz.getLevelQuestion()))
//                .build();
        return questionRepository.save(question);
    }
}
