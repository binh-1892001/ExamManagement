package trainingmanagement.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import trainingmanagement.model.base.BaseModel;
import trainingmanagement.model.enums.EActiveStatus;
import trainingmanagement.model.enums.EGender;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
public class User extends BaseModel {
    private String fullName;
    private String username;
    private String password;
    private String email;
    private String phone;
    private LocalDate dateOfBirth;
    private String avatar;
    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    private EGender gender;
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private EActiveStatus status;
    // ? Relationship.
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    @JsonIgnore
    private Set<Role> roles;
    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<Result> results;
    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<UserClass> userClasses;
    @OneToMany(mappedBy = "teacher")
    @JsonIgnore
    private List<Classroom> classrooms;
}
