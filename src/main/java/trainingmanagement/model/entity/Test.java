package trainingmanagement.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import trainingmanagement.model.base.BaseModel;
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
    private Boolean status;
    private int time;
    @Enumerated(EnumType.STRING)
    private ETypeTest typeTest;
    private String resources;
    @OneToMany(mappedBy = "test")
    @JsonIgnore
    List<Question> questions ;
    @OneToMany(mappedBy = "test")
    @JsonIgnore
    List<HistoryTest> historyTests;
    @ManyToOne
    @JoinColumn(name = "examId",referencedColumnName = "id")
    private Exam exam;
}
