package ru.kharkov.eventservice.location.mappers;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.kharkov.eventservice.location.dto.LocationDto;
import ru.kharkov.eventservice.location.entities.LocationEntity;
import ru.kharkov.eventservice.location.models.Location;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class LocationMapper {

    private final ModelMapper modelMapper;

    public LocationMapper() {
        this.modelMapper = new ModelMapper();
    }

    public Location toDomain(LocationEntity locationEntity) {
        return Objects.isNull(locationEntity) ? null : this.modelMapper.map(locationEntity, Location.class);
    }

    public Location toDomain(LocationDto location) {
        return Objects.isNull(location) ? null : this.modelMapper.map(location, Location.class);
    }

    public List<Location> toDomains(List<LocationEntity> locationEntityList) {
        return locationEntityList
                .stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    public LocationDto toDto(Location location) {
        return Objects.isNull(location) ? null : this.modelMapper.map(location, LocationDto.class);
    }



    public List<LocationDto> toDtos(List<Location> locations) {
        return locations
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public LocationEntity toEntity(Location location) {
        return Objects.isNull(location) ? null : this.modelMapper.map(location, LocationEntity.class);
    }
}