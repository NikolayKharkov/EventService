package ru.kharkov.eventservice.location;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("locations")
public class LocationController {

    @Autowired
    private LocationService locationService;

    @Autowired
    private LocationMapper locationMapper;

    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @GetMapping
    public ResponseEntity<List<LocationDto>> getAllLocations() {
        List<LocationDto> result = locationMapper.toDtos(locationService.getAllLocations());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public ResponseEntity<LocationDto> createLocation(@Valid @RequestBody LocationDto locationDto) {
        Location saved = this.locationMapper.toDomain(locationDto);
        saved = this.locationService.createLocation(saved);
        LocationDto result = this.locationMapper.toDto(saved);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("{id}")
    public ResponseEntity<LocationDto> deleteLocation(@PathVariable("id") long locationId) {
        Location deletedLocation = this.locationService.deleteLocation(locationId);
        LocationDto result = this.locationMapper.toDto(deletedLocation);
        return new ResponseEntity<>(result, HttpStatus.NO_CONTENT);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @GetMapping("{id}")
    public ResponseEntity<LocationDto> getLocationById(@PathVariable("id") long locationId) {
        Location locationById = this.locationService.getLocationById(locationId);
        LocationDto result = this.locationMapper.toDto(locationById);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("{id}")
    public ResponseEntity<LocationDto> updateLocation(@PathVariable("id") long locationId,
                                                      @Valid @RequestBody LocationDto locationDto) {
        Location updatedLocation = this.locationMapper.toDomain(locationDto);
        updatedLocation.setId(locationId);
        Location location = this.locationService.updateLocation(updatedLocation);
        LocationDto result = this.locationMapper.toDto(location);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
