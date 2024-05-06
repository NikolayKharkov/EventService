package ru.kharkov.eventservice.event;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.kharkov.eventservice.event.dto.EventDto;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class EventMapper {

    @Autowired
    private ModelMapper modelMapper;

    public Event toDomain (EventDto eventDto) {
        return Objects.isNull(eventDto) ? null : this.modelMapper.map(eventDto, Event.class);
    }

    public Event toDomain (EventEntity eventEntity) {
        return Objects.isNull(eventEntity) ? null : this.modelMapper.map(eventEntity, Event.class);
    }

    public EventDto toDto(Event event) {
        return Objects.isNull(event) ? null : this.modelMapper.map(event, EventDto.class);
    }

    public EventEntity toEntity(Event event) {
        return Objects.isNull(event) ? null : this.modelMapper.map(event, EventEntity.class);
    }

    public List<Event> toDomains(List<EventEntity> eventEntityList) {
        return eventEntityList
                .stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    public List<EventDto> toDtos(List<Event> eventEntityList) {
        return eventEntityList
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }


}
