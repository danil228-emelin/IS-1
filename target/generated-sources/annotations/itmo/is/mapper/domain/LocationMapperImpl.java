package itmo.is.mapper.domain;

import itmo.is.dto.domain.LocationDto;
import itmo.is.model.domain.Location;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-12-04T20:27:18+0300",
    comments = "version: 1.6.2, compiler: javac, environment: Java 17.0.9 (Amazon.com Inc.)"
)
@Component
public class LocationMapperImpl implements LocationMapper {

    @Override
    public Location toEntity(LocationDto dto) {
        if ( dto == null ) {
            return null;
        }

        Location location = new Location();

        location.setX( dto.x() );
        location.setY( dto.y() );
        location.setZ( dto.z() );

        return location;
    }

    @Override
    public LocationDto toDto(Location entity) {
        if ( entity == null ) {
            return null;
        }

        double x = 0.0d;
        Double y = null;
        double z = 0.0d;

        x = entity.getX();
        y = entity.getY();
        z = entity.getZ();

        LocationDto locationDto = new LocationDto( x, y, z );

        return locationDto;
    }
}
