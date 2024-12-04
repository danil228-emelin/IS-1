package itmo.is.repository;

import itmo.is.model.domain.Color;
import itmo.is.model.domain.Country;
import itmo.is.model.domain.Person;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PersonRepository extends JpaRepository<Person, Integer> {
    Page<Person> findAllByNameContaining(@NotNull String name, @NotNull Pageable pageable);

    long countByWeightEquals(@NotNull Integer weight);

    long countByWeightLessThan(@NotNull Integer weight);

    @Query("SELECT DISTINCT p.nationality FROM Person p")
    List<Country> findDistinctNationalities();

    long countByEyeColorEquals(@NotNull Color color);
}


