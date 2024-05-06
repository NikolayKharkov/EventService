package ru.kharkov.eventservice.event.dto;

import lombok.*;
import ru.kharkov.eventservice.event.EventStatus;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class SearchEventFilterDto {

        private int durationMax;

        private int durationMin;

        private LocalDateTime dateStartBefore;

        private LocalDateTime dateStartAfter;

        private int placesMin;

        private int placesMax;

        private Long locationId;

        private EventStatus eventStatus;

        private String name;

        private int costMin;

        private int costMax;

}
