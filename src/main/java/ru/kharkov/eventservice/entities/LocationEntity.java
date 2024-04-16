package ru.kharkov.eventservice.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
@Entity
@Table(name = "locations")
public class LocationEntity {

     @Id
     @Column(name = "id")
     @GeneratedValue(strategy = GenerationType.IDENTITY)
     private Long id;

     @Column(name = "name")
     private String name;

     @Column(name = "address")
     private String address;

     @Column(name = "capacity")
     private int capacity;

     @Column(name = "description")
     private String description;


     @Override
     public boolean equals(Object o) {
          if (this == o) return true;
          if (o == null || getClass() != o.getClass()) return false;

          LocationEntity that = (LocationEntity) o;

          return Objects.equals(id, that.id);
     }

     @Override
     public int hashCode() {
          return id != null ? id.hashCode() : 0;
     }
}

