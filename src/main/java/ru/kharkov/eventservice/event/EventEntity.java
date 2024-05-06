package ru.kharkov.eventservice.event;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
@Entity
@Table(name = "events")
public class EventEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "event_date")
    private LocalDateTime eventDate;

    @Column(name = "duration")
    private int duration;

    @Column(name = "cost")
    private int cost;

    @Column(name = "max_places")
    private int maxPlaces;

    @Column(name = "location_id")
    private Long locationId;

    @Column(name = "name")
    private String name;

    @Column(name = "event_status")
    private EventStatus eventStatus;

    @Column(name = "owner_id")
    private Long ownerId;

    @Column(name = "occupied_places")
    private int occupiedPlaces;


}
