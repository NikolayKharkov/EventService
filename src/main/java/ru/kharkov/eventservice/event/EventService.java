package ru.kharkov.eventservice.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kharkov.eventservice.event.dto.SearchEventFilterDto;
import ru.kharkov.eventservice.location.Location;
import ru.kharkov.eventservice.location.LocationService;
import ru.kharkov.eventservice.security.SecurityService;
import ru.kharkov.eventservice.user.Role;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;

@Service
@Transactional
public class EventService {

    @Autowired
    private EventMapper eventMapper;

    @Autowired
    private LocationService locationService;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private SecurityService securityService;

    public Event createEvent(Event event) {
        Location location = this.locationService.getLocationById(event.getLocationId());
        if (event.getMaxPlaces() > location.getCapacity()) {
            throw new IllegalArgumentException(
                    String.format("Локация %s имеет максимальную вместительность %s",
                            location.getName(),
                            location.getCapacity()));
        }
        Long userAuthId = this.securityService.getUserAuthId();
        validateEventDateOrThrow(event, location);
        event.setEventStatus(EventStatus.WAIT_START);
        event.setOwnerId(userAuthId);
        EventEntity eventEntity = this.eventMapper.toEntity(event);
        EventEntity saved = this.eventRepository.save(eventEntity);
        return this.eventMapper.toDomain(saved);
    }

    public void cancelEvent(Long eventId) {
        Event event = getEventById(eventId);
        if (event.getEventStatus().equals(EventStatus.WAIT_START)) {
            throwIfNotHasPermission(event);
            this.eventRepository.updateStatusById(eventId, EventStatus.CANCELLED);
            return;
        }
        throw new IllegalArgumentException(String.format("Мероприятие с статусом %s, не возможно отменить", event.getEventStatus()));

    }

    public Event updateEventById(Long eventId, Event eventUpdated) {
        Event curEvent = this.getEventById(eventId);
        if (curEvent.getEventStatus().equals(EventStatus.WAIT_START)) {
            throwIfNotHasPermission(curEvent);
            validateEventOrThrow(curEvent, eventUpdated);
            curEvent.setEventDate(eventUpdated.getEventDate());
            curEvent.setCost(eventUpdated.getCost());
            curEvent.setDuration(eventUpdated.getDuration());
            curEvent.setMaxPlaces(eventUpdated.getMaxPlaces());
            curEvent.setLocationId(eventUpdated.getLocationId());
            curEvent.setName(eventUpdated.getName());
            EventEntity entity = this.eventMapper.toEntity(curEvent);
            this.eventRepository.save(entity);
            return curEvent;
        }
        throw new IllegalArgumentException(String.format("Мероприятие с статусом %s, не возможно обновить", curEvent.getEventStatus()));
    }

    public Event getEventById(Long eventId) {
        EventEntity eventEntity = this.eventRepository
                .findById(eventId)
                .orElseThrow(() -> new NoSuchElementException(String.format("Мероприятия с id:%s не существует.", eventId)));
        return this.eventMapper.toDomain(eventEntity);
    }

    public void addUserToEvent(Long eventId) {
        this.eventRepository.incrementOccupiedByEventId(eventId);
    }

    public void removeUserFromEvent(Long eventId) {
        this.eventRepository.decrementOccupiedByEventId(eventId);
    }

    public List<Event> getEventsByFilter(SearchEventFilterDto searchEventFilterDto) {
        List<EventEntity> eventsByFilter =
                this.eventRepository.getEventsByFilter(
                        searchEventFilterDto.getDurationMax(),
                        searchEventFilterDto.getDurationMin(),
                        searchEventFilterDto.getDateStartBefore(),
                        searchEventFilterDto.getDateStartAfter(),
                        searchEventFilterDto.getPlacesMin(),
                        searchEventFilterDto.getPlacesMax(),
                        searchEventFilterDto.getLocationId(),
                        searchEventFilterDto.getEventStatus(),
                        searchEventFilterDto.getName(),
                        searchEventFilterDto.getCostMin(),
                        searchEventFilterDto.getCostMax());
        return this.eventMapper.toDomains(eventsByFilter);
    }

    public List<Event> getUsersEvents() {
        long userId = this.securityService.getUserAuthId();
        List<EventEntity> usersEvents = this.eventRepository.getUsersEvents(userId);
        return this.eventMapper.toDomains(usersEvents);

    }

    public List<Event> getMyEventsByRegs() {
        Long userId = this.securityService.getUserAuthId();
        List<EventEntity> usersEventsByReg = this.eventRepository.getUsersEventsByReg(userId);
        return this.eventMapper.toDomains(usersEventsByReg);
    }

    public void updateEventsStatus() {
        this.eventRepository.updateEventStatus(
                EventStatus.PASSING,
                EventStatus.FINISHED,
                EventStatus.WAIT_START);
    }

    private void throwIfNotHasPermission(Event event) {
        Set<Role> usersAuthRoles = this.securityService.getUsersAuthRoles();
        Long userAuthId = this.securityService.getUserAuthId();
        if (usersAuthRoles.contains(Role.ADMIN) || event.getOwnerId().equals(userAuthId)) {
            return;
        }
        throw new AccessDeniedException("Нет прав для работы с мероприятием");
    }

    private void validateEventOrThrow(Event curEvent, Event updatedEvent) {
        Location location = this.locationService.getLocationById(updatedEvent.getLocationId());
        int curOccupiedPlaces = curEvent.getOccupiedPlaces();
        int maxPlaces = updatedEvent.getMaxPlaces();
        int locationCapacity = location.getCapacity();
        if (locationCapacity < maxPlaces) {
            throw new IllegalArgumentException(String.format("Максимальная вместительность локации %s равно %s.", location.getName(), location.getCapacity()));
        }
        if (locationCapacity < curOccupiedPlaces) {
            throw new IllegalArgumentException("На мероприятие купленно место больше чем вместительность локации");
        }
        if (maxPlaces < curOccupiedPlaces) {
            throw new IllegalArgumentException("На мероприятие купленно место больше чем завернное число посетителей");
        }
        updatedEvent.setId(curEvent.getId());
        validateEventDateOrThrow(updatedEvent, location);
    }

    private void validateEventDateOrThrow(Event event, Location location) {
        LocalDateTime dateTo = event.getEventDate().plusMinutes(event.getDuration());

        List<EventStatus> statusList = List.of(EventStatus.WAIT_START, EventStatus.PASSING);

        int eventsCount = Objects.isNull(event.getId()) ?
                eventRepository.countEventsByLocationAndDateRange(event.getEventDate(), dateTo, statusList, location.getId()) :
                eventRepository.countEventsByLocationAndDateRange(event.getEventDate(), dateTo, statusList, location.getId(), event.getId());

        if (eventsCount > 0) {
            throw new IllegalArgumentException(
                    String.format("Локация %s занята на указанное время",
                            location.getName()));
        }
    }

}
