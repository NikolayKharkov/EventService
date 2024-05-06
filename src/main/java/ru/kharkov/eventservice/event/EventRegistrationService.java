package ru.kharkov.eventservice.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kharkov.eventservice.security.SecurityService;

import java.util.Optional;

@Service
@Transactional
public class EventRegistrationService {

    @Autowired
    private EventService eventService;

    @Autowired
    private RegistrationRepository registrationRepository;

    @Autowired
    private SecurityService securityService;

    public void regToEvent(Long eventId) {
        Event event = this.eventService.getEventById(eventId);
        if (event.getEventStatus().equals(EventStatus.WAIT_START)) {
            Long userAuthId = this.securityService.getUserAuthId();
            if (event.getOwnerId().equals(userAuthId)) {
                throw new IllegalArgumentException("Не возможно зарегистрироваться на мероприятие, в котором вы организатор");
            }
            int maxPlaces = event.getMaxPlaces();
            int occupiedPlaces = event.getOccupiedPlaces();
            if (maxPlaces > occupiedPlaces) {
                RegistrationEntity registrationEntity = RegistrationEntity
                        .builder()
                        .eventId(eventId)
                        .userId(userAuthId)
                        .build();
                this.registrationRepository.save(registrationEntity);
                this.eventService.addUserToEvent(eventId);
                return;
            }
            throw new IllegalArgumentException("Все свободные места на мероприятие уже заняты");
        }
        throw new IllegalArgumentException(String.format("Мероприятие %s не доступно для регистрации. Статус мероприятия: %s",
                event.getName(),
                event.getEventStatus()));
    }

    public void cancellationReg(Long eventId) {
        Event event = this.eventService.getEventById(eventId);
        if (event.getEventStatus().equals(EventStatus.WAIT_START)) {
            Long userAuthId = this.securityService.getUserAuthId();
            RegistrationEntity registrationEntity = this.registrationRepository
                    .findByUserIdAndEventId(userAuthId, eventId)
                    .orElseThrow(() -> new IllegalArgumentException(String.format("Вы не зарегистрированы на мероприятие %s", event.getName())));
            this.eventService.removeUserFromEvent(eventId);
            this.registrationRepository.delete(registrationEntity);
            return;
        }
        throw new IllegalArgumentException(String.format("Отменить регистрацию на мероприятие %s не возможно. Статус мероприятия: %s",
                event.getName(),
                event.getEventStatus()));
    }
}
