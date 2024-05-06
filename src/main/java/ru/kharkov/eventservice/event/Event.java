package ru.kharkov.eventservice.event;

import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class Event {

    private Long id;

    private LocalDateTime eventDate;

    private int duration;

    private int cost;

    private int maxPlaces;

    private Long locationId;

    private String name;

    private EventStatus eventStatus;

    private Long ownerId;

    private int occupiedPlaces;
}


