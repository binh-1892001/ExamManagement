package trainingmanagement.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import trainingmanagement.model.base.BaseModel;
import trainingmanagement.model.entity.Enum.ETypeQuestion;

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
    private ETypeQuestion typeQuestion;
    private Boolean status;
    @ManyToOne
    @JoinColumn(name = "testId",referencedColumnName = "id")
    private Test test;

    @OneToMany(mappedBy = "question")
    @JsonIgnore
    private List<Option> options;
}
