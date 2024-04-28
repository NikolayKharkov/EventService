package ru.kharkov.eventservice.utils;

import lombok.*;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Класс отвечает за отображения ошибки
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ErrorResponse {

    private String message;

    private String detailMessage;

    private LocalDateTime dateTime;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ErrorResponse that = (ErrorResponse) o;

        if (!Objects.equals(message, that.message)) return false;
        if (!Objects.equals(detailMessage, that.detailMessage))
            return false;
        return Objects.equals(dateTime, that.dateTime);
    }

    @Override
    public int hashCode() {
        int result = message != null ? message.hashCode() : 0;
        result = 31 * result + (detailMessage != null ? detailMessage.hashCode() : 0);
        result = 31 * result + (dateTime != null ? dateTime.hashCode() : 0);
        return result;
    }
}
