package itmo.is.dto.domain.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import itmo.is.dto.domain.StudyGroupDto;

public record AddPersonToGroupRequest(
        @JsonProperty(value = "group_id", required = true)
        Long group_id,

        @JsonProperty(value = "person_id", required = false)
        Integer person_id
) {
}
