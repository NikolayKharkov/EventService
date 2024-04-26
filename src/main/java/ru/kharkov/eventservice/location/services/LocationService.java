package ru.kharkov.eventservice.location.services;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kharkov.eventservice.location.entities.LocationEntity;
import ru.kharkov.eventservice.location.models.Location;
import ru.kharkov.eventservice.location.repositories.LocationRepository;
import ru.kharkov.eventservice.location.mappers.LocationMapper;

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

    private static final Logger LOG = LogManager.getLogger(LocationService.class);


    public List<Location> getAllLocations() {
        LOG.info("Вызов получения всех локаций");
        return locationMapper.toDomains(locationRepository.findAll());
    }

    public Location createLocation(Location location) {
        LOG.info("Вызов создание локации");
        if (Objects.nonNull(location.getId())) {
            throw new IllegalArgumentException("Не возможно создать локацию с указанным ID.");
        }
        LocationEntity saved = this.locationMapper.toEntity(location);
        saved = this.locationRepository.save(saved);
        LOG.info(String.format("Локация успеша создана. %s", saved));
        return this.locationMapper.toDomain(saved);
    }

    public Location getLocationById(long locationId) {
        LOG.info("Вызов поулчения локации по id");
        LocationEntity result = this.locationRepository
                .findById(locationId)
                .orElseThrow(() -> new NoSuchElementException(String.format("Локации с id:%s не существует", locationId)));
        return this.locationMapper.toDomain(result);
    }

    public Location deleteLocation(long locationId) {
        LOG.info("Вызов удаления локации");
        Location deletedLocation = this.getLocationById(locationId);
        this.locationRepository.deleteById(locationId);
        LOG.info(String.format("Локация успеша удалена. %s", deletedLocation));
        return deletedLocation;
    }


    public Location updateLocation(Location updatedLocation) {
        LOG.info("Вызов обновления локации");
        getLocationById(updatedLocation.getId());
        LocationEntity updatedLocationEntity = this.locationMapper.toEntity(updatedLocation);
        this.locationRepository.updateById(updatedLocationEntity.getId(),
                updatedLocationEntity.getName(),
                updatedLocationEntity.getAddress(),
                updatedLocationEntity.getCapacity(),
                updatedLocationEntity.getDescription());
        LOG.info(String.format("Локация успеша обновлена. %s", updatedLocation));
        return updatedLocation;
    }
}
