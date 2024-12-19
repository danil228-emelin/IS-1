package itmo.is.mapper.domain;

import itmo.is.dto.domain.CoordinatesDto;
import itmo.is.model.domain.Coordinates;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-12-11T11:49:03+0300",
    comments = "version: 1.6.2, compiler: javac, environment: Java 17.0.9 (Amazon.com Inc.)"
)
@Component
public class CoordinatesMapperImpl implements CoordinatesMapper {

    @Override
    public Coordinates toEntity(CoordinatesDto dto) {
        if ( dto == null ) {
            return null;
        }

        Coordinates coordinates = new Coordinates();

        coordinates.setX( dto.x() );
        coordinates.setY( dto.y() );

        return coordinates;
    }

    @Override
    public CoordinatesDto toDto(Coordinates entity) {
        if ( entity == null ) {
            return null;
        }

        Integer x = null;
        int y = 0;

        x = entity.getX();
        y = entity.getY();

        CoordinatesDto coordinatesDto = new CoordinatesDto( x, y );

        return coordinatesDto;
    }
}
