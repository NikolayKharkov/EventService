package ru.kharkov.eventservice.utils.mappers;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.kharkov.eventservice.dto.LocationDto;
import ru.kharkov.eventservice.entities.LocationEntity;
import ru.kharkov.eventservice.models.Location;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class LocationMapper {

    private final ModelMapper modelMapper;

    public LocationMapper() {
        this.modelMapper = new ModelMapper();
    }

    public Location toModel(LocationEntity locationEntity) {
        return Objects.isNull(locationEntity) ? null : this.modelMapper.map(locationEntity, Location.class);
    }

    public Location toModel(LocationDto location) {
        return Objects.isNull(location) ? null : this.modelMapper.map(location, Location.class);
    }

    public List<Location> toModels(List<LocationEntity> locationEntityList) {
        return locationEntityList
                .stream()
                .map(this::toModel)
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
