package itmo.is.repository;

import itmo.is.model.domain.Person;
import itmo.is.model.domain.StudyGroup;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface StudyGroupRepository extends JpaRepository<StudyGroup, Long> {
    Page<StudyGroup> findAllByNameContaining(@NotNull String name, @NotNull Pageable pageable);

    long countStudyGroupsByAverageMarkLessThanEqual(@NotNull Double averageMark);

    @Query(value = "SELECT s.groupAdmin FROM StudyGroup s ORDER BY s.groupAdmin ASC LIMIT 1")
    int findElementWithMinGroupId();

}
