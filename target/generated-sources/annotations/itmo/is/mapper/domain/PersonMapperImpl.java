package itmo.is.mapper.domain;

import itmo.is.dto.authentication.UserDto;
import itmo.is.dto.domain.CoordinatesDto;
import itmo.is.dto.domain.LocationDto;
import itmo.is.dto.domain.PersonDto;
import itmo.is.dto.domain.StudyGroupDto;
import itmo.is.dto.domain.request.CreatePersonRequest;
import itmo.is.dto.domain.request.UpdatePersonRequest;
import itmo.is.model.domain.Color;
import itmo.is.model.domain.Country;
import itmo.is.model.domain.Person;
import itmo.is.model.security.User;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-12-05T00:10:52+0300",
    comments = "version: 1.6.2, compiler: javac, environment: Java 17.0.9 (Amazon.com Inc.)"
)
@Component
public class PersonMapperImpl implements PersonMapper {

    @Autowired
    private CoordinatesMapper coordinatesMapper;
    @Autowired
    private LocationMapper locationMapper;
    @Autowired
    private StudyGroupMapper studyGroupMapper;

    @Override
    public Person toEntity(PersonDto dto) {
        if ( dto == null ) {
            return null;
        }

        Person person = new Person();

        person.setOwner( userDtoToUser( dto.owner() ) );
        person.setAdminEditAllowed( dto.adminEditAllowed() );
        person.setId( dto.id() );
        person.setName( dto.name() );
        person.setCoordinates( coordinatesMapper.toEntity( dto.coordinates() ) );
        person.setStudyGroup( studyGroupMapper.toEntity( dto.studyGroup() ) );
        person.setEyeColor( dto.eyeColor() );
        person.setLocation( locationMapper.toEntity( dto.location() ) );
        person.setWeight( dto.weight() );
        person.setNationality( dto.nationality() );

        return person;
    }

    @Override
    public PersonDto toDto(Person entity) {
        if ( entity == null ) {
            return null;
        }

        int id = 0;
        String name = null;
        StudyGroupDto studyGroup = null;
        CoordinatesDto coordinates = null;
        Color eyeColor = null;
        LocationDto location = null;
        Integer weight = null;
        Country nationality = null;
        UserDto owner = null;
        boolean adminEditAllowed = false;

        id = entity.getId();
        name = entity.getName();
        studyGroup = studyGroupMapper.toDto( entity.getStudyGroup() );
        coordinates = coordinatesMapper.toDto( entity.getCoordinates() );
        eyeColor = entity.getEyeColor();
        location = locationMapper.toDto( entity.getLocation() );
        weight = entity.getWeight();
        nationality = entity.getNationality();
        owner = userToUserDto( entity.getOwner() );
        adminEditAllowed = entity.isAdminEditAllowed();

        PersonDto personDto = new PersonDto( id, name, studyGroup, coordinates, eyeColor, location, weight, nationality, owner, adminEditAllowed );

        return personDto;
    }

    @Override
    public Person toEntity(CreatePersonRequest createPersonRequest) {
        if ( createPersonRequest == null ) {
            return null;
        }

        Person person = new Person();

        person.setAdminEditAllowed( createPersonRequest.adminEditAllowed() );
        person.setName( createPersonRequest.name() );
        person.setCoordinates( coordinatesMapper.toEntity( createPersonRequest.coordinates() ) );
        person.setEyeColor( createPersonRequest.eyeColor() );
        person.setLocation( locationMapper.toEntity( createPersonRequest.location() ) );
        person.setWeight( createPersonRequest.weight() );
        person.setNationality( createPersonRequest.nationality() );

        return person;
    }

    @Override
    public Person toEntity(UpdatePersonRequest updatePersonRequest) {
        if ( updatePersonRequest == null ) {
            return null;
        }

        Person person = new Person();

        person.setName( updatePersonRequest.name() );
        person.setCoordinates( coordinatesMapper.toEntity( updatePersonRequest.coordinates() ) );
        person.setEyeColor( updatePersonRequest.eyeColor() );
        person.setLocation( locationMapper.toEntity( updatePersonRequest.location() ) );
        person.setWeight( updatePersonRequest.weight() );
        person.setNationality( updatePersonRequest.nationality() );

        return person;
    }

    protected User userDtoToUser(UserDto userDto) {
        if ( userDto == null ) {
            return null;
        }

        User.UserBuilder user = User.builder();

        user.id( userDto.id() );
        user.username( userDto.username() );

        return user.build();
    }

    protected UserDto userToUserDto(User user) {
        if ( user == null ) {
            return null;
        }

        Long id = null;
        String username = null;

        id = user.getId();
        username = user.getUsername();

        UserDto userDto = new UserDto( id, username );

        return userDto;
    }
}
