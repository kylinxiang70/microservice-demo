package org.kylin.userservice.init;

import org.kylin.userservice.entity.User;
import org.kylin.userservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class UserDataInit implements CommandLineRunner {
    private UserRepository userRepository;

    @Autowired
    public UserDataInit(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        User user = User.builder()
                .username("Tom")
                .email("tom@example.com")
                .phoneNumber("8613900000000")
                .build();
        User user2 = User.builder()
                .username("Bob")
                .email("bob@example.com")
                .phoneNumber("8613911111111")
                .build();
        userRepository.saveAll(Arrays.asList(user, user2));
    }
}
