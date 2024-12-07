package itmo.is.mapper.domain;

import itmo.is.dto.domain.StudyGroupDto;
import itmo.is.dto.domain.request.CreateStudyGroupRequest;
import itmo.is.mapper.EntityMapper;
import itmo.is.model.domain.StudyGroup;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {CoordinatesMapper.class,})

public interface StudyGroupMapper extends EntityMapper<StudyGroupDto, StudyGroup> {
    StudyGroup toEntity(CreateStudyGroupRequest createStudyGroupRequest);


}
