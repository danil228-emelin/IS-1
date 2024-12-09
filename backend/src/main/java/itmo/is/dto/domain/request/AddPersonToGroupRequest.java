package itmo.is.dto.domain.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public record AddPersonToGroupRequest(
        @JsonProperty(value = "group_id", required = true)
        Integer group_id,

        @JsonProperty(value = "person_id", required = true)
        Integer person_id
) {
}
