package trainingmanagement.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import trainingmanagement.model.base.BaseModel;
import trainingmanagement.model.entity.Enum.EActiveStatus;
import trainingmanagement.model.entity.Enum.ETestType;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Test extends BaseModel {
    private String testName;
    private Integer testTime;
    @Enumerated(EnumType.STRING)
    private ETestType testType;
    private String resources;
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private EActiveStatus status;
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
