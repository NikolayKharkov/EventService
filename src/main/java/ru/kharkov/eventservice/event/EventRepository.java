package ru.kharkov.eventservice.event;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<EventEntity, Long> {

    @Query(value = "SELECT count(e) FROM events e" +
            " where e.event_date <= :toDate and (e.event_date + (e.duration * interval '1 minute')) >= :fromDate" +
            " and e.event_status in :statusList" +
            " and e.location_id = :locationId", nativeQuery = true)
    int countEventsByLocationAndDateRange(@Param(value = "fromDate") LocalDateTime fromDate,
                                                 @Param(value = "toDate") LocalDateTime toDate,
                                                 @Param(value = "statusList") List<EventStatus> statusList,
                                                 @Param(value = "locationId") Long locationId);

    @Query(value = "SELECT count(e) FROM events e" +
            " where e.event_date <= :toDate and (e.event_date + (e.duration * interval '1 minute')) >= :fromDate" +
            " and e.event_status in :statusList" +
            " and e.location_id = :locationId" +
            " and e.id != :eventId", nativeQuery = true)
    int countEventsByLocationAndDateRange(@Param(value = "fromDate") LocalDateTime fromDate,
                                          @Param(value = "toDate") LocalDateTime toDate,
                                          @Param(value = "statusList") List<EventStatus> statusList,
                                          @Param(value = "locationId") Long locationId,
                                          @Param(value = "eventId") Long eventId);

    @Query("select e from EventEntity e " +
            "where e.duration between :durationMin and :durationMax " +
            "and e.eventDate between :dateStartBefore and :dateStartAfter " +
            "and e.maxPlaces between :placesMin and :placesMax " +
            "and e.locationId = :locationId " +
            "and e.eventStatus = :eventStatus " +
            "and e.name like %:name% " +
            "and e.cost between :costMin and :costMax")
    List<EventEntity> getEventsByFilter(@Param(value = "durationMax") int durationMax,
                                        @Param(value = "durationMin") int durationMin,
                                        @Param(value = "dateStartBefore") LocalDateTime dateStartBefore,
                                        @Param(value = "dateStartAfter") LocalDateTime dateStartAfter,
                                        @Param(value = "placesMin") int placesMin,
                                        @Param(value = "placesMax") int placesMax,
                                        @Param(value = "locationId") Long locationId,
                                        @Param(value = "eventStatus") EventStatus eventStatus,
                                        @Param(value = "name") String name,
                                        @Param(value = "costMin") int costMin,
                                        @Param(value = "costMax") int costMax);


    @Query("select e from EventEntity e " +
            "where e.ownerId = :userId")
    List<EventEntity> getUsersEvents(@Param(value = "userId") Long userId);

    @Modifying
    @Query("update EventEntity e " +
            "set e.occupiedPlaces = e.occupiedPlaces + 1" +
            "where e.id = :eventId")
    void incrementOccupiedByEventId(@Param(value = "eventId") Long eventId);

    @Modifying
    @Query("update EventEntity e " +
            "set e.occupiedPlaces = e.occupiedPlaces - 1" +
            "where e.id = :eventId")
    void decrementOccupiedByEventId(@Param(value = "eventId") Long eventId);

    @Modifying
    @Query("update EventEntity e " +
            "set e.eventStatus = :eventStatus " +
            "where e.id = :id")
    void updateStatusById(@Param(value = "id") long id,
                          @Param(value = "eventStatus") EventStatus eventStatus);

    @Query("select ee from EventEntity ee " +
            "join fetch RegistrationEntity re on re.eventId = ee.id " +
            " where re.userId = :userId")
    List<EventEntity> getUsersEventsByReg(@Param(value = "userId") Long userId);

    @Modifying
    @Query(value = "UPDATE events " +
            "SET event_status = CASE " +
            "                       WHEN event_date <= CURRENT_TIMESTAMP and event_status = :waitStart THEN  :passingStatus " +
            "                       WHEN (event_date + (duration * interval '1 minute')) <= CURRENT_TIMESTAMP and event_status = :passingStatus THEN :finishedStatus " +
            "                       ELSE event_status " +
            "    END where event_status = :waitStart or event_status = :passingStatus", nativeQuery = true)
    void updateEventStatus(@Param(value = "passingStatus") EventStatus passingStatus,
                           @Param(value = "finishedStatus") EventStatus finishedStatus,
                           @Param(value = "waitStart") EventStatus waitStart);

}
