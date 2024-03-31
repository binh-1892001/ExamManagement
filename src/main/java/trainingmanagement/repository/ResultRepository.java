package trainingmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import trainingmanagement.model.entity.Result;
import trainingmanagement.model.entity.Test;
import trainingmanagement.model.entity.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface ResultRepository extends JpaRepository<Result,Long> {
    List<Result> getAllByUser(User user);
    // Lấy ra danh sách kết quả của teacher quản lý
    @Query(value = "SELECT r.* FROM result r join user u on r.user_id=u.id " +
            "join user_class uc on u.id=uc.user_id where uc.class_id =:classId ",nativeQuery = true)
    List<Result> findAllByClassId(Long classId);
    // tìm kiếm kết quả sinh viên theo fullName
//    @Query(value = "SELECT r.* FROM result r join user u on r.user_id=u.id " +
//            "join user_class uc on u.id=uc.user_id where uc.class_id =:classId r.user.fullName LIKE CONCAT('%', :keyword, '%') and r.teacher = :teacher",nativeQuery = true)
//    List<Result> findByStudentFullName(String keyword, User teacher);
    Optional<Result> findByIdAndTeacher(Long id,User teacher);
    void deleteByIdAndTeacher(Long id,User teacher);
    List<Result> findAllByUserAndTest(User user, Test test);
}
