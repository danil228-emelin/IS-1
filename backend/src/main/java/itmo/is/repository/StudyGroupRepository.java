package itmo.is.repository;

import itmo.is.model.domain.Person;
import itmo.is.model.domain.StudyGroup;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudyGroupRepository extends JpaRepository<StudyGroup, Long> {
    Page<StudyGroup> findAllByNameContaining(@NotNull String name, @NotNull Pageable pageable);

    long countStudyGroupsByAverageMarkLessThanEqual(@NotNull Double averageMark);


}
