package trainingmanagement.model.entity;

import jakarta.persistence.*;
import lombok.*;
import trainingmanagement.model.base.BaseModel;
import trainingmanagement.model.entity.Enum.ERoleName;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Role extends BaseModel {
    @Enumerated(EnumType.STRING)
    private ERoleName roleName;
}