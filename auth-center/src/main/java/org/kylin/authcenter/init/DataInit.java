package org.kylin.authcenter.init;

import org.kylin.authcenter.constant.AuthConstant;
import org.kylin.authcenter.entity.User;
import org.kylin.authcenter.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashSet;
import java.util.UUID;

@Component
public class DataInit implements CommandLineRunner {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        User user = User.builder()
                .userId(UUID.randomUUID().toString().replace("-", ""))
                .username("user")
                .password(passwordEncoder.encode("111111"))
                .roles(new HashSet<>(Arrays.asList(AuthConstant.ROLE_USER)))
                .build();

        User admin = User.builder()
                .userId(UUID.randomUUID().toString().replace("-", ""))
                .username("admin")
                .password(passwordEncoder.encode("111111"))
                .roles(new HashSet<>(Arrays.asList(AuthConstant.ROLE_USER, AuthConstant.ROLE_ADMIN)))
                .build();
        userRepository.saveAll(Arrays.asList(user, admin));
    }
}
