package trainingmanagement.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import trainingmanagement.model.base.BaseModel;
import trainingmanagement.model.entity.Enum.EActiveStatus;
import trainingmanagement.model.entity.Enum.EClassStatus;
import java.util.List;

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
    @Column(name = "class_status")
    private EClassStatus classStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private EActiveStatus status;

    @ManyToOne
    @JoinColumn(name = "teacherId",referencedColumnName = "id")
    private User teacher;

    @OneToMany(mappedBy = "classroom")
    @JsonIgnore
    private List<ClassSubject> classSubjects;

    @OneToMany(mappedBy = "classroom")
    @JsonIgnore
    private List<UserClass> userClasses;
}