package itmo.is.mapper.domain;

import itmo.is.dto.domain.CoordinatesDto;
import itmo.is.dto.domain.StudyGroupDto;
import itmo.is.dto.domain.request.CreateStudyGroupRequest;
import itmo.is.model.domain.FormOfEducation;
import itmo.is.model.domain.Semester;
import itmo.is.model.domain.StudyGroup;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-12-09T22:46:57+0300",
    comments = "version: 1.6.2, compiler: javac, environment: Java 17.0.9 (Amazon.com Inc.)"
)
@Component
public class StudyGroupMapperImpl implements StudyGroupMapper {

    @Autowired
    private CoordinatesMapper coordinatesMapper;

    @Override
    public StudyGroup toEntity(StudyGroupDto dto) {
        if ( dto == null ) {
            return null;
        }

        StudyGroup studyGroup = new StudyGroup();

        studyGroup.setGroupAdmin( dto.groupAdmin() );
        studyGroup.setId( (long) dto.id() );
        studyGroup.setName( dto.name() );
        studyGroup.setStudentsCount( dto.studentsCount() );
        studyGroup.setFormOfEducation( dto.formOfEducation() );
        studyGroup.setAverageMark( dto.averageMark() );
        studyGroup.setSemesterEnum( dto.semesterEnum() );
        studyGroup.setCoordinates( coordinatesMapper.toEntity( dto.coordinates() ) );

        return studyGroup;
    }

    @Override
    public StudyGroupDto toDto(StudyGroup entity) {
        if ( entity == null ) {
            return null;
        }

        int id = 0;
        String name = null;
        CoordinatesDto coordinates = null;
        long studentsCount = 0L;
        int groupAdmin = 0;
        FormOfEducation formOfEducation = null;
        double averageMark = 0.0d;
        Semester semesterEnum = null;

        if ( entity.getId() != null ) {
            id = entity.getId().intValue();
        }
        name = entity.getName();
        coordinates = coordinatesMapper.toDto( entity.getCoordinates() );
        studentsCount = entity.getStudentsCount();
        groupAdmin = entity.getGroupAdmin();
        formOfEducation = entity.getFormOfEducation();
        averageMark = entity.getAverageMark();
        semesterEnum = entity.getSemesterEnum();

        StudyGroupDto studyGroupDto = new StudyGroupDto( id, name, coordinates, studentsCount, groupAdmin, formOfEducation, averageMark, semesterEnum );

        return studyGroupDto;
    }

    @Override
    public StudyGroup toEntity(CreateStudyGroupRequest createStudyGroupRequest) {
        if ( createStudyGroupRequest == null ) {
            return null;
        }

        StudyGroup studyGroup = new StudyGroup();

        studyGroup.setGroupAdmin( createStudyGroupRequest.groupAdmin() );
        studyGroup.setName( createStudyGroupRequest.name() );
        studyGroup.setStudentsCount( createStudyGroupRequest.studentsCount() );
        studyGroup.setFormOfEducation( createStudyGroupRequest.formOfEducation() );
        studyGroup.setAverageMark( createStudyGroupRequest.averageMark() );
        studyGroup.setSemesterEnum( createStudyGroupRequest.semesterEnum() );
        studyGroup.setCoordinates( coordinatesMapper.toEntity( createStudyGroupRequest.coordinates() ) );

        return studyGroup;
    }
}
