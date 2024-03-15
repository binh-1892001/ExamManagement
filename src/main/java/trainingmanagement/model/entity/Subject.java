package trainingmanagement.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
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
    @Column(name = "subject_name")
    private String subjectName;
    @Column(name = "status")
    private Boolean status;
    // * Class - Subject: 1 - N.
    @OneToMany(mappedBy = "subject")
    @JsonIgnore
    List<Exam> exams;
}
