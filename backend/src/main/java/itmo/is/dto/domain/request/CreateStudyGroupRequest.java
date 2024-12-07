package itmo.is.dto.domain.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import itmo.is.dto.domain.CoordinatesDto;
import itmo.is.model.domain.FormOfEducation;
import itmo.is.model.domain.Semester;

public record CreateStudyGroupRequest(
    @JsonProperty(value = "coordinates", required = true)
    CoordinatesDto coordinates,

    @JsonProperty(value = "name", required = true)
     String name,
    @JsonProperty(value = "students_count", required = true)
     long studentsCount,

    @JsonProperty(value = "form_of_education", required = true)

     FormOfEducation formOfEducation,
    @JsonProperty(value = "average_mark", required = true)

     double averageMark,


    @JsonProperty(value = "admin", required = true)
    int groupAdmin,

    @JsonProperty(value = "semester_enum", required = true)

     Semester semesterEnum)

{

}
