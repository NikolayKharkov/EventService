package ru.kharkov.eventservice.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kharkov.eventservice.entities.LocationEntity;
import ru.kharkov.eventservice.models.Location;
import ru.kharkov.eventservice.repositories.LocationRepository;
import ru.kharkov.eventservice.utils.mappers.LocationMapper;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

@Service
@Transactional
public class LocationService {

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private LocationMapper locationMapper;

    public List<Location> getAllLocations() {
        return locationMapper.toModels(locationRepository.findAll());
    }

    public Location createLocation(Location location) {
        if (Objects.nonNull(location.getId())) {
            throw new IllegalArgumentException("Не возможно создать локацию с указанным ID.");
        }
        LocationEntity saved = this.locationMapper.toEntity(location);
        saved = this.locationRepository.save(saved);
        return this.locationMapper.toModel(saved);
    }

    public Location getLocationById(long locationId) {
        LocationEntity result = this.locationRepository
                .findById(locationId)
                .orElseThrow(() -> new NoSuchElementException(String.format("Локации с id:%s не существует", locationId)));
        return this.locationMapper.toModel(result);
    }

    public void deleteLocation(long locationId) {
        this.locationRepository.deleteById(locationId);
    }


    public Location updateLocation(Location updatedLocation) {
        getLocationById(updatedLocation.getId());
        return createLocation(updatedLocation);
    }
}
