package trainingmanagement.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import trainingmanagement.model.base.BaseModel;
import trainingmanagement.model.enums.EActiveStatus;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
public class Subject extends BaseModel {
    @Column(name = "subject_name")
    private String subjectName;
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private EActiveStatus status;
    // ? Relationship.
    // * Class - Subject: 1 - N.
    @OneToMany(mappedBy = "subject")
    @JsonIgnore
    List<Exam> exams;
    @OneToMany(mappedBy = "subject")
    @JsonIgnore
    private List<ClassSubject> classSubjects;
}
