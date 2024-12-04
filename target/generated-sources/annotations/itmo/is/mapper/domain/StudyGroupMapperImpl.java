package itmo.is.mapper.domain;

import itmo.is.dto.domain.StudyGroupDto;
import itmo.is.model.domain.Coordinates;
import itmo.is.model.domain.FormOfEducation;
import itmo.is.model.domain.Semester;
import itmo.is.model.domain.StudyGroup;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-12-04T18:07:25+0300",
    comments = "version: 1.6.2, compiler: javac, environment: Java 17.0.9 (Amazon.com Inc.)"
)
@Component
public class StudyGroupMapperImpl implements StudyGroupMapper {

    @Override
    public StudyGroupDto toDto(StudyGroup entity) {
        if ( entity == null ) {
            return null;
        }

        Integer id = null;
        String name = null;
        long studentsCount = 0L;
        FormOfEducation formOfEducation = null;
        double averageMark = 0.0d;
        Semester semesterEnum = null;

        if ( entity.getId() != null ) {
            id = entity.getId().intValue();
        }
        name = entity.getName();
        studentsCount = entity.getStudentsCount();
        formOfEducation = entity.getFormOfEducation();
        averageMark = entity.getAverageMark();
        semesterEnum = entity.getSemesterEnum();

        Coordinates coordinates = null;

        StudyGroupDto studyGroupDto = new StudyGroupDto( id, name, coordinates, studentsCount, formOfEducation, averageMark, semesterEnum );

        return studyGroupDto;
    }

    @Override
    public StudyGroup toEntity(StudyGroupDto createGroupRequest) {
        if ( createGroupRequest == null ) {
            return null;
        }

        StudyGroup studyGroup = new StudyGroup();

        if ( createGroupRequest.id() != null ) {
            studyGroup.setId( createGroupRequest.id().longValue() );
        }
        studyGroup.setName( createGroupRequest.name() );
        studyGroup.setStudentsCount( createGroupRequest.studentsCount() );
        studyGroup.setFormOfEducation( createGroupRequest.formOfEducation() );
        studyGroup.setAverageMark( createGroupRequest.averageMark() );
        studyGroup.setSemesterEnum( createGroupRequest.semesterEnum() );

        return studyGroup;
    }
}
