package trainingmanagement.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import trainingmanagement.model.base.BaseModel;
import trainingmanagement.model.entity.Enum.EActiveStatus;

import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Subject extends BaseModel {
    private String subjectName;
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private EActiveStatus eActiveStatus;
    // * Class - Subject: 1 - N.
    @OneToMany(mappedBy = "subject")
    @JsonIgnore
    List<Exam> exams;

    @OneToMany(mappedBy = "subject")
    @JsonIgnore
    private List<ClassSubject> classSubjects;
}
