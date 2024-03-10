package trainingmanagement.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import trainingmanagement.model.base.BaseModel;

import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class HistoryTest extends BaseModel {
    @ManyToOne
    @JoinColumn(name = "studentId",referencedColumnName = "id")
    private User student;

    @ManyToOne
    @JoinColumn(name = "teacherId",referencedColumnName = "id")
    private User teacher;

    @ManyToOne
    @JoinColumn(name = "testId",referencedColumnName = "id")
    private Test test;
}
