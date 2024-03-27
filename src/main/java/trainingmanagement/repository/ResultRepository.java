package trainingmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import trainingmanagement.model.entity.Result;
import trainingmanagement.model.entity.User;

import java.util.List;

@Repository
public interface ResultRepository extends JpaRepository<Result,Long> {
    List<Result> getAllByUser(User user);
}
