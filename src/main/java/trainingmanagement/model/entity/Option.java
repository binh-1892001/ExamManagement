package trainingmanagement.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import trainingmanagement.model.base.BaseModel;
import trainingmanagement.model.entity.Enum.EActiveStatus;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "options")
public class Option extends BaseModel {
    private String contentOptions;
    private EActiveStatus status;
    @ManyToOne
    @JoinColumn(name = "question_id",referencedColumnName = "id")
    private Question question;
}
