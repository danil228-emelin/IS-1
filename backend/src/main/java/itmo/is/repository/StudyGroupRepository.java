package itmo.is.repository;

import itmo.is.model.domain.StudyGroup;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudyGroupRepository extends JpaRepository<StudyGroup, Long> {
    long countStudyGroupByAverageMarkEquals (@NotNull Double averageMark);

}
