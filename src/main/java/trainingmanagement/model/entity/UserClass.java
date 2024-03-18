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
public class UserClass extends BaseModel {
    @ManyToOne
    @JoinColumn(name = "userId",referencedColumnName = "id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "classId",referencedColumnName = "id")
    private Classroom classroom;
}