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
public class Exam extends BaseModel {
    private String examName;
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private EActiveStatus status;
    @ManyToOne
    @JoinColumn(name = "subject_id",referencedColumnName = "id")
    private Subject subject;
    @OneToMany(mappedBy = "exam")
    @JsonIgnore
    private List<Test> tests;
}