package trainingmanagement.model.entity;

import jakarta.persistence.*;
import lombok.*;
import trainingmanagement.model.base.BaseModel;
import trainingmanagement.model.entity.Enum.EActiveStatus;

import java.time.LocalDate;
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Result extends BaseModel {
    @ManyToOne
    @JoinColumn(name = "student_id",referencedColumnName = "id")
    private User student;
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private EActiveStatus eActiveStatus;
    @ManyToOne
    @JoinColumn(name = "teacher_id",referencedColumnName = "id")
    private User teacher;
    @ManyToOne
    @JoinColumn(name = "test_id",referencedColumnName = "id")
    private Test test;
    private LocalDate dateDoTest;
    private Double mark;
    private Integer examTimes;
}
