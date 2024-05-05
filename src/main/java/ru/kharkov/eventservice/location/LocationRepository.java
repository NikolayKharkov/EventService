package ru.kharkov.eventservice.location;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationRepository extends JpaRepository<LocationEntity, Long> {


    @Modifying
    @Query("update LocationEntity l " +
            "set l.name = :name," +
            " l.address = :address," +
            " l.capacity = :capacity," +
            " l.description = :description  " +
            "where l.id = :id")
    void updateById(@Param(value = "id") long id,
                    @Param(value = "name") String locationName,
                    @Param(value = "address") String address,
                    @Param(value = "capacity") int capacity,
                    @Param(value = "description") String description);

}
