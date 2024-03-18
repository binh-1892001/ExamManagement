package trainingmanagement.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import trainingmanagement.model.base.BaseModel;
import trainingmanagement.model.enums.EActiveStatus;

import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Subject extends BaseModel {
    @Column(name = "subject_name")
    private String subjectName;
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private EActiveStatus status;
    // * Class - Subject: 1 - N.
    @OneToMany(mappedBy = "subject")
    @JsonIgnore
    List<Exam> exams;
}
