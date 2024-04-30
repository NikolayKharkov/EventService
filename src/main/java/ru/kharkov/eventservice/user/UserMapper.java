package ru.kharkov.eventservice.user;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.kharkov.eventservice.user.dto.UserDto;
import ru.kharkov.eventservice.user.dto.UserInfoDto;

import java.util.Objects;

@Component
public class UserMapper {

    @Autowired
    private ModelMapper modelMapper;

    public User toDomain (UserDto user) {
        return Objects.isNull(user) ? null : this.modelMapper.map(user, User.class);
    }

    public User toDomain (UserEntity user) {
        return Objects.isNull(user) ? null : this.modelMapper.map(user, User.class);
    }

    public UserDto toDto (User user) {
        return Objects.isNull(user) ? null : this.modelMapper.map(user, UserDto.class);
    }

    public UserInfoDto toUserInfoDto(User user) {
        return Objects.isNull(user) ? null : this.modelMapper.map(user, UserInfoDto.class);
    }

    public UserDto toDto (UserEntity user) {
        return Objects.isNull(user) ? null : this.modelMapper.map(user, UserDto.class);
    }

    public UserEntity toEntity (User user) {
        return Objects.isNull(user) ? null : this.modelMapper.map(user, UserEntity.class);
    }

}
