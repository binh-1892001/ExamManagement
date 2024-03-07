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
    private String nameSubject;
    private String timeToStudy;
    private Boolean status;
    private int time;

    @OneToMany(mappedBy = "subject")
    @JsonIgnore
    List<Exam> exams;
}
