package trainingmanagement.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import trainingmanagement.model.base.BaseModel;
import trainingmanagement.model.entity.Enum.EActiveStatus;
import trainingmanagement.model.entity.Enum.ELevelQuestion;
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
    @Enumerated(EnumType.STRING)
    private ELevelQuestion levelQuestion;
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private EActiveStatus eActiveStatus;
    @ManyToOne
    @JoinColumn(name = "test_id", referencedColumnName = "id")
    private Test test;
    @OneToMany(mappedBy = "question")
    @JsonIgnore
    private List<Option> options;
}
