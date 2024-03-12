package trainingmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import trainingmanagement.model.entity.Enum.ERoles;
import trainingmanagement.model.entity.UserClass;

import java.util.List;

@Repository
public interface UserClassRepository extends JpaRepository<UserClass,Long> {
    @Query(value = "select * from user_class uc join user u on uc.userId=u.id " +
            "join user_role ur on u.id=ur.user_id join role r on ur.role_id=r.id " +
            "where r.role_name=:eRoles",nativeQuery = true)
    List<UserClass> findAllByRole(ERoles eRoles);

}
