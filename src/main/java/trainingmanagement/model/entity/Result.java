package trainingmanagement.model.entity;

import jakarta.persistence.*;
import lombok.*;
import trainingmanagement.model.base.BaseModel;
import trainingmanagement.model.enums.EActiveStatus;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
public class Result extends BaseModel {
    @ManyToOne
    @JoinColumn(name = "student_id",referencedColumnName = "id")
    private User student;
    @ManyToOne
    @JoinColumn(name = "teacher_id",referencedColumnName = "id")
    private User teacher;
    @ManyToOne
    @JoinColumn(name = "test_id",referencedColumnName = "id")
    private Test test;
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private EActiveStatus status;
    private LocalDate dateDoTest;
    private Double mark;
    private Integer examTimes;
}
