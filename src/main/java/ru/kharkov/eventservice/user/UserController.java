package ru.kharkov.eventservice.user;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.kharkov.eventservice.user.dto.JWTResponse;
import ru.kharkov.eventservice.user.dto.LogInRequest;
import ru.kharkov.eventservice.user.dto.UserSignUpRequest;
import ru.kharkov.eventservice.user.dto.UserInfoDto;

@RestController
@RequestMapping("users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthService authService;

    @Autowired
    private UserMapper userMapper;

    @PostMapping
    public ResponseEntity<UserSignUpRequest> signUp(@Valid @RequestBody UserSignUpRequest userSignUpRequest) {
        User userCreated = this.userMapper.toDomain(userSignUpRequest);
        User result = this.authService.sighUp(userCreated);
        return new ResponseEntity<>(this.userMapper.toDto(result), HttpStatus.CREATED);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("{id}")
    public ResponseEntity<UserInfoDto> getUserInfo(@PathVariable("id") Long userId) {
        User user = this.userService.getUserByIdOrThrow(userId);
        UserInfoDto result = this.userMapper.toUserInfoDto(user);
        return new ResponseEntity<>(result, HttpStatus.FOUND);
    }

    @PostMapping("auth")
    public ResponseEntity<JWTResponse> logIn(@RequestBody LogInRequest logInRequest) {
        JWTResponse result = this.authService.logIn(logInRequest);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
