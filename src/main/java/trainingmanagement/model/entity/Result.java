package trainingmanagement.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.cglib.core.internal.LoadingCache;
import trainingmanagement.model.base.BaseModel;

import java.time.LocalDate;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Result extends BaseModel {
    @ManyToOne
    @JoinColumn(name = "studentId",referencedColumnName = "id")
    private User student;

    @ManyToOne
    @JoinColumn(name = "teacherId",referencedColumnName = "id")
    private User teacher;

    @ManyToOne
    @JoinColumn(name = "testId",referencedColumnName = "id")
    private Test test;

    private LocalDate dateDoTest;
    private Double mark;
    private Integer examTimes;
}
