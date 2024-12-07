package itmo.is.dto.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import itmo.is.model.domain.FormOfEducation;
import itmo.is.model.domain.Semester;


public record StudyGroupDto(
        @JsonProperty("id")
        int id,
        @JsonProperty("name")

        String name,
        @JsonProperty("coordinates")

        CoordinatesDto coordinates,

        @JsonProperty("students_count")
        long studentsCount,

        @JsonProperty("admin")
        int groupAdmin,
        @JsonProperty("form_of_education")
        FormOfEducation formOfEducation,
        @JsonProperty("average_mark")

        double averageMark,

        @JsonProperty("semester_enum")
        Semester semesterEnum
) {
}
