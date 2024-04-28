package ru.kharkov.eventservice.location.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class LocationDto {

    private Long id;

    @NotNull(message = "Имя локации не должно быть пустым.")
    private String name;

    @NotNull(message = "Адресс локации не должен быть пустым.")
    private String address;

    @Positive(message = "Вместимость локации должна быть больше нуля.")
    private int capacity;

    @NotNull(message = "Описание локации не должно быть пустым.")
    private String description;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LocationDto that = (LocationDto) o;

        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
