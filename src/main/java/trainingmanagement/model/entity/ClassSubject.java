package trainingmanagement.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;
import trainingmanagement.model.base.BaseModel;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
public class ClassSubject extends BaseModel {
    @ManyToOne
    @JoinColumn(name = "classId",referencedColumnName = "id")
    private Classroom classroom;

    @ManyToOne
    @JoinColumn(name = "subjectId",referencedColumnName = "id")
    private Subject subject;
}