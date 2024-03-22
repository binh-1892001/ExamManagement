package trainingmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import trainingmanagement.model.entity.Result;

@Repository
public interface ResultRepository extends JpaRepository<Result,Long> {
}
