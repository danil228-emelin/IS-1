package itmo.is.dto.domain.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import itmo.is.dto.domain.CoordinatesDto;
import itmo.is.dto.domain.LocationDto;
import itmo.is.model.domain.Color;
import itmo.is.model.domain.Country;

import java.time.LocalDate;

public record UpdatePersonRequest(
        @JsonProperty(value = "name", required = true)
        String name,

        @JsonProperty(value = "coordinates", required = true)
        CoordinatesDto coordinates,

        @JsonProperty(value = "eye_color", required = false)
        Color eyeColor,
        @JsonProperty(value = "location", required = true)
        LocationDto location,

        @JsonProperty(value = "weight", required = false)
        Integer weight,

        @JsonProperty(value = "nationality", required = false)
        Country nationality
) {
}
