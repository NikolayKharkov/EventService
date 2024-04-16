package ru.kharkov.eventservice.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kharkov.eventservice.dto.LocationDto;
import ru.kharkov.eventservice.models.Location;
import ru.kharkov.eventservice.services.LocationService;
import ru.kharkov.eventservice.utils.mappers.LocationMapper;

import java.util.List;

@RestController
@RequestMapping("/locations")
public class LocationController {

    @Autowired
    private LocationService locationService;

    @Autowired
    private LocationMapper locationMapper;

    @GetMapping
    public ResponseEntity<List<LocationDto>> getAllLocations() {
        List<LocationDto> result = locationMapper.toDtos(locationService.getAllLocations());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<LocationDto> createLocation(@Valid @RequestBody LocationDto locationDto) {
        Location saved = this.locationMapper.toModel(locationDto);
        saved = this.locationService.createLocation(saved);
        return new ResponseEntity<>(this.locationMapper.toDto(saved), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<LocationDto> deleteLocation(@PathVariable("id") long locationId) {
        ResponseEntity<LocationDto> locationById = this.getLocationById(locationId);
        this.locationService.deleteLocation(locationId);
        return locationById;
    }

    @GetMapping("/{id}")
    public ResponseEntity<LocationDto> getLocationById(@PathVariable("id") long locationId) {
        return new ResponseEntity<>(this.locationMapper.toDto(this.locationService.getLocationById(locationId)), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<LocationDto> updateLocation(@PathVariable("id") long locationId,
                                                      @Valid @RequestBody LocationDto locationDto) {
        Location updatedLocation = this.locationMapper.toModel(locationDto);
        updatedLocation.setId(locationId);
        return new ResponseEntity<>(this.locationMapper.toDto(this.locationService.updateLocation(updatedLocation)), HttpStatus.OK);
    }
}
