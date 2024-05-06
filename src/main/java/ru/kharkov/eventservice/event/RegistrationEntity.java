package ru.kharkov.eventservice.event;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
@Entity
@Table(name = "registrations")
public class RegistrationEntity {


    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "events_id")
    private Long eventId;

    @Column(name = "user_id")
    private Long userId;
}
