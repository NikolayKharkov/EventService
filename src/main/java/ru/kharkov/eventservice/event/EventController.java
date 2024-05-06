package ru.kharkov.eventservice.event;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.kharkov.eventservice.event.dto.EventDto;
import ru.kharkov.eventservice.event.dto.SearchEventFilterDto;

import java.util.List;

@RestController
@RequestMapping("events")
public class EventController {


    @Autowired
    private EventMapper eventMapper;

    @Autowired
    private EventService eventService;

    @Autowired
    private EventRegistrationService eventRegistrationService;

    @PostMapping
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<EventDto> createEvent(@Valid @RequestBody EventDto createEventDto) {
        Event event = this.eventMapper.toDomain(createEventDto);
        Event created = this.eventService.createEvent(event);
        EventDto result = this.eventMapper.toDto(created);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @DeleteMapping("{eventId}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public ResponseEntity cancelEvent(@PathVariable("eventId") long eventId) {
        this.eventService.cancelEvent(eventId);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @GetMapping("{eventId}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public ResponseEntity<EventDto> getEventById(@PathVariable("eventId") long eventId) {
        EventDto result = this.eventMapper.toDto(this.eventService.getEventById(eventId));
        return new ResponseEntity(result, HttpStatus.FOUND);
    }

    @PutMapping("{eventId}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public ResponseEntity<EventDto> getEventById(@Valid @RequestBody EventDto updateEventDto,
                                                 @PathVariable("eventId") long eventId) {
        Event updatedEvent = this.eventMapper.toDomain(updateEventDto);
        Event event = this.eventService.updateEventById(eventId, updatedEvent);
        EventDto result = this.eventMapper.toDto(event);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("search")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public ResponseEntity<List<EventDto>> getEventsBySearchFilter(@RequestBody SearchEventFilterDto searchEventFilterDto) {
        List<Event> eventsByFilter = this.eventService.getEventsByFilter(searchEventFilterDto);
        return new ResponseEntity<>(this.eventMapper.toDtos(eventsByFilter), HttpStatus.FOUND);
    }

    @GetMapping("/my")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<List<EventDto>> getMyEvents() {
        List<Event> usersEvents = this.eventService.getUsersEvents();
        return new ResponseEntity<>(this.eventMapper.toDtos(usersEvents), HttpStatus.FOUND);
    }

    @PostMapping("/registrations/{eventId}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public ResponseEntity regToEvent(@PathVariable("eventId") long eventId) {
        this.eventRegistrationService.regToEvent(eventId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/registrations/{eventId}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public ResponseEntity cancelReg(@PathVariable("eventId") long eventId) {
        this.eventRegistrationService.cancellationReg(eventId);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/registrations/my")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public ResponseEntity<List<EventDto>> getMyRegs() {
        List<Event> myEventsByRegs = this.eventService.getMyEventsByRegs();
        return new ResponseEntity<>(this.eventMapper.toDtos(myEventsByRegs), HttpStatus.FOUND);

    }

}
