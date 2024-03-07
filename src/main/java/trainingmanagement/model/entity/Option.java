package trainingmanagement.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import trainingmanagement.model.base.BaseModel;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "Options")
public class Option extends BaseModel {
    private String contentOptions;
    private Boolean status;
    @ManyToOne
    @JoinColumn(name = "question_id",referencedColumnName = "id")
    private Question question;
}
