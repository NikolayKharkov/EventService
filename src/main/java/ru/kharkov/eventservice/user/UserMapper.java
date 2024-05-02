package ru.kharkov.eventservice.user;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.kharkov.eventservice.user.dto.UserSignUpRequest;
import ru.kharkov.eventservice.user.dto.UserInfoDto;

import java.util.Objects;

@Component
public class UserMapper {

    @Autowired
    private ModelMapper modelMapper;

    public User toDomain (UserSignUpRequest user) {
        return Objects.isNull(user) ? null : this.modelMapper.map(user, User.class);
    }

    public User toDomain (UserEntity user) {
        return Objects.isNull(user) ? null : this.modelMapper.map(user, User.class);
    }

    public UserSignUpRequest toDto (User user) {
        return Objects.isNull(user) ? null : this.modelMapper.map(user, UserSignUpRequest.class);
    }

    public UserInfoDto toUserInfoDto(User user) {
        return Objects.isNull(user) ? null : this.modelMapper.map(user, UserInfoDto.class);
    }

    public UserSignUpRequest toDto (UserEntity user) {
        return Objects.isNull(user) ? null : this.modelMapper.map(user, UserSignUpRequest.class);
    }

    public UserEntity toEntity (User user) {
        return Objects.isNull(user) ? null : this.modelMapper.map(user, UserEntity.class);
    }

}
