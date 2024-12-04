package itmo.is.dto.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import itmo.is.dto.authentication.UserDto;
import itmo.is.model.domain.Color;
import itmo.is.model.domain.Country;

public record PersonDto(
        @JsonProperty("id")
        int id,

        @JsonProperty("name")
        String name,

        @JsonProperty("coordinates")
        CoordinatesDto coordinates,

        @JsonProperty("eye_color")
        Color eyeColor,

        @JsonProperty("location")
        LocationDto location,

        @JsonProperty("weight")
        Integer weight,

        @JsonProperty("nationality")
        Country nationality,

        @JsonProperty("owner")
        UserDto owner,

        @JsonProperty("admin_edit_allowed")
        boolean adminEditAllowed
) {
}
