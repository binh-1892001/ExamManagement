package trainingmanagement.model;

import jakarta.persistence.Entity;
import lombok.*;
import trainingmanagement.model.base.BaseModel;

import java.time.LocalDate;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Users extends BaseModel {
	private String fullName;
	private String username;
	private String password;
	private LocalDate dateOfBirth;
	private boolean status;
}
