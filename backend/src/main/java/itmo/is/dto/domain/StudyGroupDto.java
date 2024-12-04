package itmo.is.dto.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import itmo.is.model.domain.Coordinates;
import itmo.is.model.domain.FormOfEducation;
import itmo.is.model.domain.Semester;


public record StudyGroupDto(
    @JsonProperty("study_id")
     Integer id,
    @JsonProperty("study_name")

     String name,
    @JsonProperty("study_coordinates")

     Coordinates coordinates,

    @JsonProperty("study_students_count")
     long studentsCount,

    @JsonProperty("study_form_of_education")
     FormOfEducation formOfEducation,
    @JsonProperty("study_average_mark")

     double averageMark,

    @JsonProperty("study_semester_enum")
     Semester semesterEnum
){}
