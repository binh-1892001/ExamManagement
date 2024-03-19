package trainingmanagement.model.entity;

import jakarta.persistence.*;
import lombok.*;
import trainingmanagement.model.base.BaseModel;
import trainingmanagement.model.enums.EActiveStatus;
import trainingmanagement.model.enums.EOptionStatus;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "options")
public class Option extends BaseModel {
    private String optionContent;
    @Enumerated(EnumType.STRING)
    @Column(name = "is_correct")
    private EOptionStatus isCorrect;
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private EActiveStatus status;
    @ManyToOne
    @JoinColumn(name = "question_id",referencedColumnName = "id")
    private Question question;
}
