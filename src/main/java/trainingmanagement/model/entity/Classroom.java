/**
 * * The Classroom is entity for class table in database.
 * @param className (class_name column in database).
 * @param status (status column in database): to soft delete.
 *
 * @author NguyenDucHai.
 * @since 13/3/2024.
 * */

package trainingmanagement.model.entity;

import jakarta.persistence.*;
import lombok.*;
import trainingmanagement.model.base.BaseModel;
import trainingmanagement.model.enums.EActiveStatus;
import trainingmanagement.model.enums.EClassStatus;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Classroom extends BaseModel {
    @Column(name = "class_name")
    private String className;
    @Enumerated(EnumType.STRING)
    @Column(name = "class_status")
    private EClassStatus classStatus;
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private EActiveStatus status;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "class_subject",
            joinColumns = @JoinColumn(name = "class_id"),
            inverseJoinColumns = @JoinColumn(name = "subject_id")
    )
    private Set<Subject> subjects;
}