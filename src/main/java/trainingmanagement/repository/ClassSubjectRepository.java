package trainingmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import trainingmanagement.model.entity.ClassSubject;
import trainingmanagement.model.entity.Classroom;
import trainingmanagement.model.entity.Subject;
import trainingmanagement.model.entity.UserClass;

import java.util.List;

@Repository
public interface ClassSubjectRepository extends JpaRepository<ClassSubject,Long> {
    ClassSubject findByClassroomAndSubject(Classroom classroom, Subject subject);

    @Query("select us from ClassSubject us where us.classroom.id = :classId")
    List<ClassSubject> findSubjectByClassId(Long classId);

    @Query("select us from ClassSubject us where us.subject.id = :subjectId")
    List<ClassSubject> findClassBySubjectId(Long subjectId);

    ClassSubject findByClassroom(Classroom classroom);


}
