package ru.kharkov.eventservice.user;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    private static final Logger logger = LogManager.getLogger(UserService.class);


    public User createUserOrThrow(User createUser) {
        createUser.setRole(Role.USER);
        createUser.setId(null);
        logger.info("Вызов метода создании пользователя.");
        if (this.userExistByLogin(createUser.getLogin())) {
            logger.error(String.format("Пользователь не создан. Логин: \"%s\" уже занят.", createUser.getLogin()));
            throw new IllegalArgumentException(String.format("Пользователь с логином: \"%s\" уже существует.", createUser.getLogin()));
        }
        UserEntity userEntity = this.userMapper.toEntity(createUser);
        userEntity = this.userRepository.save(userEntity);
        createUser.setId(userEntity.getId());
        createUser.setPassword(userEntity.getPassword());
        logger.debug(String.format("Пользовтель успешно создан: %s", userEntity));
        return createUser;
    }

    public User getUserByIdOrThrow(long userId) {
        Optional<UserEntity> byId = this.userRepository.findById(userId);
        UserEntity userEntity = byId
                .orElseThrow(() -> new NoSuchElementException(String.format("Пользователя с id:%s не существует.", userId)));
        return this.userMapper.toDomain(userEntity);
    }

    public boolean userExistByLogin(String userLogin) {
        return this.userRepository.existsByLogin(userLogin);
    }

    public User getUserByLoginOrThrow(String userLogin) {
        Optional<UserEntity> byId = this.userRepository.findByLogin(userLogin);
        UserEntity userEntity = byId
                .orElseThrow(() -> new NoSuchElementException(String.format("Пользователя с логином:%s не существует.", userLogin)));
        return this.userMapper.toDomain(userEntity);
    }

}
