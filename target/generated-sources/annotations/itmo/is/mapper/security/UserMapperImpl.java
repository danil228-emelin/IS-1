package itmo.is.mapper.security;

import itmo.is.dto.authentication.RegisterRequest;
import itmo.is.dto.authentication.UserDto;
import itmo.is.model.security.User;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-12-04T20:27:18+0300",
    comments = "version: 1.6.2, compiler: javac, environment: Java 17.0.9 (Amazon.com Inc.)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public User toEntity(UserDto dto) {
        if ( dto == null ) {
            return null;
        }

        User.UserBuilder user = User.builder();

        user.id( dto.id() );
        user.username( dto.username() );

        return user.build();
    }

    @Override
    public UserDto toDto(User entity) {
        if ( entity == null ) {
            return null;
        }

        Long id = null;
        String username = null;

        id = entity.getId();
        username = entity.getUsername();

        UserDto userDto = new UserDto( id, username );

        return userDto;
    }

    @Override
    public User toEntity(RegisterRequest registerRequest) {
        if ( registerRequest == null ) {
            return null;
        }

        User.UserBuilder user = User.builder();

        user.username( registerRequest.username() );
        user.password( registerRequest.password() );

        return user.build();
    }
}
