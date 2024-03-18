/**
 * * Created by Tung.
 * * Fixed by NguyenHongQuan:
 * * - Rename attributes in entity.
 * @author: Phạm Văn Tùng.
 * @since: 15/3/2024.
 * */
package trainingmanagement.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import trainingmanagement.model.base.BaseModel;
import trainingmanagement.model.enums.EActiveStatus;
import trainingmanagement.model.enums.ETestType;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
public class Test extends BaseModel {
    @Column(name = "test_name")
    private String testName;
    @Column(name = "test_time")
    private Integer testTime;
    @Enumerated(EnumType.STRING)
    @Column(name = "test_type")
    private ETestType testType;
    @Column(name = "resources")
    private String resources;
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private EActiveStatus status;
    @OneToMany(mappedBy = "test")
    @JsonIgnore
    List<Question> questions;
    @OneToMany(mappedBy = "test")
    @JsonIgnore
    List<Result> historyTests;
    @ManyToOne
    @JoinColumn(name = "exam_id", referencedColumnName = "id")
    private Exam exam;
}