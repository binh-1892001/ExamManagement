package trainingmanagement.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import trainingmanagement.model.base.BaseModel;
import trainingmanagement.model.enums.EActiveStatus;
import trainingmanagement.model.enums.EQuestionLevel;
import trainingmanagement.model.enums.EQuestionType;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
public class Question extends BaseModel {
    private String questionContent;
    @Enumerated(EnumType.STRING)
    @Column(name = "question_type")
    private EQuestionType questionType;
    @Enumerated(EnumType.STRING)
    @Column(name = "question_level")
    private EQuestionLevel questionLevel;
    private String image;
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private EActiveStatus status;
    @ManyToOne
    @JoinColumn(name = "test_id", referencedColumnName = "id")
    private Test test;
    @OneToMany(mappedBy = "question")
    @JsonIgnore
    private List<Option> options;
}
