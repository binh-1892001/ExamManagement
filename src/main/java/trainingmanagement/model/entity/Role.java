package trainingmanagement.model.entity;

import jakarta.persistence.*;
import lombok.*;
import trainingmanagement.model.base.BaseModel;
import trainingmanagement.model.enums.ERoleName;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Role extends BaseModel {
    @Enumerated(EnumType.STRING)
    @Column(name = "role_name")
    private ERoleName roleName;
}