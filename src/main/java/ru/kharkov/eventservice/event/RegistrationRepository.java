package ru.kharkov.eventservice.event;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RegistrationRepository extends JpaRepository<RegistrationEntity, Long> {

    Optional<RegistrationEntity> findByUserIdAndEventId(Long userId, Long eventId);

    boolean existsByEventIdAndAndUserId(Long eventId, Long userId);
}
