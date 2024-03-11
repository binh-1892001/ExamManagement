package trainingmanagement.model.entity;

import jakarta.persistence.*;
import lombok.*;
import trainingmanagement.model.base.BaseModel;
import trainingmanagement.model.entity.Enum.EStatusClass;
<<<<<<< HEAD

=======
>>>>>>> developer
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Classroom extends BaseModel {
    @Column(name = "class_name")
    private String className;
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private EStatusClass status;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "class_subject",
            joinColumns = @JoinColumn(name = "class_id"),
            inverseJoinColumns = @JoinColumn(name = "subject_id")
    )
    private Set<Subject> subjects;
}