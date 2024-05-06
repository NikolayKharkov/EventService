package ru.kharkov.eventservice.event.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;
import ru.kharkov.eventservice.event.EventStatus;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class EventDto {

    private Long id;

    @JsonFormat(pattern = "yyyy-dd-MM'T'HH:mm:ss", shape = JsonFormat.Shape.STRING)
    @Future(message = "Некорректная дата мероприятия")
    @NotNull(message = "Дата не передана")
    private LocalDateTime eventDate;

    @Positive(message = "Длительность мероприятия не может быть отрицательным")
    private int duration;

    @Positive(message = "Стоимость мероприятия не может быть отрицательным")
    private int cost;

    @Positive(message = "Стоимость мероприятия не может быть отрицательным")
    private int maxPlaces;

    private Long locationId;

    @NotEmpty(message = "Не указано имя мероприятия")
    private String name;

    private EventStatus eventStatus;

    private Long ownerId;

    private int occupiedPlaces;
}
