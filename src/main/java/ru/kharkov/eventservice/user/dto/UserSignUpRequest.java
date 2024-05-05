package ru.kharkov.eventservice.user.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class UserSignUpRequest {

    private Long id;

    @NotNull(message = "Логин пользователя не должен быть пустым.")
    private String login;

    @NotEmpty(message = "Не указан пароль.")
    private String password;

    @Positive(message = "Возраст не должен быть больше нуля")
    private int age;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserSignUpRequest userSignUpRequest = (UserSignUpRequest) o;

        if (!Objects.equals(id, userSignUpRequest.id)) return false;
        return login.equals(userSignUpRequest.login);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + login.hashCode();
        return result;
    }
}
