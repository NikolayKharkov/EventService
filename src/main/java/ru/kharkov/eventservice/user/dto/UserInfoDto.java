package ru.kharkov.eventservice.user.dto;

import lombok.*;
import ru.kharkov.eventservice.user.Role;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class UserInfoDto {

    private Long id;

    private String login;

    private Role role;
}
