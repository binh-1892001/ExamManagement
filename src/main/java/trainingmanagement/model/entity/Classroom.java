package trainingmanagement.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import trainingmanagement.model.base.BaseModel;
import trainingmanagement.model.entity.Enum.EStatusClass;

import java.util.List;
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

    @OneToMany(mappedBy = "classroom")
    @JsonIgnore
    private List<ClassSubject> classSubjects;

    @OneToMany(mappedBy = "classroom")
    @JsonIgnore
    private List<UserClass> userClasses;
}