package trainingmanagement.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
public class Exam extends BaseModel {
    private String examName;
    private EActiveStatus status;
    @ManyToOne
    @JoinColumn(name = "subject_id",referencedColumnName = "id")
    private Subject subject;
    @OneToMany(mappedBy = "exam")
    @JsonIgnore
    private List<Test> tests;
}