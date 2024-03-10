package trainingmanagement.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.*;
import trainingmanagement.model.base.BaseModel;

import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Subject extends BaseModel {
    private String subjectName;
    private String timeToStudy;
    private int time; // ? time ở đây là gì ?
    private Boolean status;
    // * Class - Subject: 1 - N.
    @OneToMany(mappedBy = "subject")
    @JsonIgnore
    List<Exam> exams;
}
