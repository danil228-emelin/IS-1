package itmo.is.dto.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import itmo.is.model.domain.FormOfEducation;
import itmo.is.model.domain.Semester;


public record StudyGroupDto(
        int id,
        @JsonProperty("study_name")

        String name,
        @JsonProperty("coordinates")

        CoordinatesDto coordinates,

        @JsonProperty("study_students_count")
        long studentsCount,

        @JsonProperty("study_form_of_education")
        FormOfEducation formOfEducation,
        @JsonProperty("study_average_mark")

        double averageMark,

        @JsonProperty("study_semester_enum")
        Semester semesterEnum
) {
    public StudyGroupDto(int id,
                         String name,
                         CoordinatesDto coordinates,
                         long studentsCount,
                         FormOfEducation formOfEducation,
                         double averageMark,
                         Semester semesterEnum) {
        this.id = id;
        this.name = name;
        this.coordinates = coordinates;
        this.studentsCount = studentsCount;
        this.formOfEducation = formOfEducation;
        this.averageMark = averageMark;
        this.semesterEnum = semesterEnum;
    }
}
