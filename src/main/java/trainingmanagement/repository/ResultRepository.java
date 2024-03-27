package trainingmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import trainingmanagement.model.entity.Result;
import trainingmanagement.model.entity.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface ResultRepository extends JpaRepository<Result,Long> {
    List<Result> getAllByUser(User user);
    // Lấy ra danh sách kết quả của teacher quản lý
    List<Result> findAllByTeacher(User teacher);
    // tìm kiếm kết quả sinh viên theo fullName
    @Query("SELECT r FROM Result r where r.user.fullName LIKE CONCAT('%', :keyword, '%') and r.teacher = :teacher")
    List<Result> findByStudentFullName(String keyword, User teacher);
    Optional<Result> findByIdAndTeacher(Long id,User teacher);
    void deleteByIdAndTeacher(Long id,User teacher);
}
