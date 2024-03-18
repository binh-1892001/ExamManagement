package trainingmanagement.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import trainingmanagement.model.base.BaseModel;
import trainingmanagement.model.enums.EActiveStatus;
import trainingmanagement.model.enums.EQuestionLevel;
import trainingmanagement.model.enums.EQuestionType;

import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Question extends BaseModel {
    private String contentQuestion;
    private String image;
    @Enumerated(EnumType.STRING)
    private EQuestionType typeQuestion;
    @Enumerated(EnumType.STRING)
    private EQuestionLevel levelQuestion;
    private EActiveStatus status;
    @ManyToOne
    @JoinColumn(name = "test_id", referencedColumnName = "id")
    private Test test;
    @OneToMany(mappedBy = "question")
    @JsonIgnore
    private List<Option> options;
}
