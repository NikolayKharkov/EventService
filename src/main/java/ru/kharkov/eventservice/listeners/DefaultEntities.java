package ru.kharkov.eventservice.listeners;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextStartedEvent;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import ru.kharkov.eventservice.user.Role;
import ru.kharkov.eventservice.user.UserEntity;
import ru.kharkov.eventservice.user.UserRepository;

@Component
public class DefaultEntities implements ApplicationListener<ContextStartedEvent> {


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder encoder;


    @Override
    public void onApplicationEvent(ContextStartedEvent event) {
        createDefaultUser("admin", "1234", Role.ADMIN);
        createDefaultUser("user_1", "1234", Role.USER);
    }


    private void createDefaultUser(String login, String password, Role role) {
        if (!this.userRepository.existsByLogin(login)) {
            String encryptedPassword = this.encoder.encode(password);
            UserEntity userEntity = UserEntity
                    .builder()
                    .password(encryptedPassword)
                    .login(login)
                    .role(role)
                    .build();
            this.userRepository.save(userEntity);
        }
    }
}
