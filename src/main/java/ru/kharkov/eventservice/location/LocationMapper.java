package ru.kharkov.eventservice.location;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class LocationMapper {

    @Autowired
    private ModelMapper modelMapper;

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