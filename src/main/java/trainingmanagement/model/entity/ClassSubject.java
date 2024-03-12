package trainingmanagement.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;
import org.springframework.data.jpa.repository.JpaRepository;
import trainingmanagement.model.base.BaseModel;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ClassSubject extends BaseModel {
    @ManyToOne
    @JoinColumn(name = "classId",referencedColumnName = "id")
    private Classroom classroom;

    @ManyToOne
    @JoinColumn(name = "subjectId",referencedColumnName = "id")
    private Subject subject;
}
