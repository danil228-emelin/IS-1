package itmo.is.mapper.domain;

import itmo.is.dto.domain.StudyGroupDto;
import itmo.is.mapper.EntityMapper;
import itmo.is.model.domain.StudyGroup;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")

public interface StudyGroupMapper extends EntityMapper<StudyGroupDto, StudyGroup> {
    StudyGroup toEntity(StudyGroupDto createGroupRequest);
}
