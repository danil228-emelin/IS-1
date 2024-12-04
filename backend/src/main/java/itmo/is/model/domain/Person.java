package itmo.is.model.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import itmo.is.model.security.OwnedEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "people")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Person extends OwnedEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    @NotEmpty
    @Column(name = "name", nullable = false,insertable=false, updatable=false)
    private String name;

    @NotNull
    @Embedded
    private Coordinates coordinates;

    // A Person can belong to only one StudyGroup
    @ManyToOne
    @JoinColumn(name = "study_group_id")
    private StudyGroup studyGroup;


    @Enumerated(EnumType.STRING)
    @Column(name = "eye_color", nullable = true)
    private Color eyeColor;

    @NotNull
    @Embedded
    private Location location;

    @Min(1)
    @Column(name = "weight", nullable = true)
    private Integer weight;

    @Enumerated(EnumType.STRING)
    @Column(name = "nationality", nullable = true)
    private Country nationality;
}
