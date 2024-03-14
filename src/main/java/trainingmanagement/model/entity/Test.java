package trainingmanagement.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import trainingmanagement.model.base.BaseModel;
import trainingmanagement.model.entity.Enum.EActiveStatus;
import trainingmanagement.model.entity.Enum.ETypeTest;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Test extends BaseModel {
    private String nameTest;
    private Integer time;
    private Integer countQuestion;
    @Enumerated(EnumType.STRING)
    private ETypeTest typeTest;
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private EActiveStatus eActiveStatus;
    private String resources;
    @OneToMany(mappedBy = "test")
    @JsonIgnore
    List<Question> questions;
    @OneToMany(mappedBy = "test")
    @JsonIgnore
    List<Result> historyTests;
    @ManyToOne
    @JoinColumn(name = "exam_id",referencedColumnName = "id")
    private Exam exam;
}
