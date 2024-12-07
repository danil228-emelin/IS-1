package itmo.is.model.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.util.List;

@Entity
@Table(name = "study_group")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class StudyGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // One StudyGroup can have many People
    @OneToMany(mappedBy = "studyGroup")
    private List<Person> persons;

    // One StudyGroup has one responsible Person
    @NotNull
    @Column(name = "group_id", nullable = false)
    private int groupAdmin;

    @NotNull
    @NotEmpty
    @Column(name = "name", nullable = false)
    private String name;

    @CreationTimestamp
    @Column(name = "creation_date", updatable = false, nullable = false)
    private java.time.ZonedDateTime creationDate;
    @Min(1)
    @Column(name = "students_count", nullable = false)
    private long studentsCount;

    @Enumerated(EnumType.STRING)
    @Column(name = "form_of_education", nullable = false)
    private FormOfEducation formOfEducation;
    @Min(1)
    @Column(name = "average_mark", nullable = false)
    private double averageMark;

    @Enumerated(EnumType.STRING)
    @Column(name = "semester_enum", nullable = false)
    private Semester semesterEnum;

    @NotNull
    @Embedded
    private Coordinates coordinates;

    public int getGroupAdmin() {
        return groupAdmin;
    }

    public void setGroupAdmin(int groupAdmin) {
        this.groupAdmin = groupAdmin;
    }
}
