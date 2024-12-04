package itmo.is.dto.domain.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import itmo.is.dto.domain.CoordinatesDto;
import itmo.is.dto.domain.LocationDto;
import itmo.is.dto.domain.StudyGroupDto;
import itmo.is.model.domain.Color;
import itmo.is.model.domain.Country;


public record CreatePersonRequest(
        @JsonProperty(value = "name", required = true)
        String name,

        @JsonProperty(value = "study_id", required = true)
        Long study_id,

        @JsonProperty(value = "coordinates", required = true)
        CoordinatesDto coordinates,

        @JsonProperty(value = "eye_color", required = false)
        Color eyeColor,

        @JsonProperty(value = "location", required = true)
        LocationDto location,

        @JsonProperty(value = "weight", required = false)
        Integer weight,
        @JsonProperty(value = "nationality", required = false)
        Country nationality,

        @JsonProperty(value = "admin_edit_allowed", required = true)
        boolean adminEditAllowed
) {
}
