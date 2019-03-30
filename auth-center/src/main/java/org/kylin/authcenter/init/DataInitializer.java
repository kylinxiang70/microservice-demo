package org.kylin.authcenter.init;

import org.kylin.authcenter.entity.User;
import org.kylin.authcenter.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashSet;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        this.userRepository.save(User.builder()
                .username("user")
                .password(this.passwordEncoder.encode("password"))
                .roles(new HashSet<>(Arrays.asList("ROLE_USER")))
                .build()
        );

        this.userRepository.save(User.builder()
                .username("admin")
                .password(this.passwordEncoder.encode("password"))
                .roles(new HashSet<>(Arrays.asList("ROLE_USER", "ROLE_ADMIN")))
                .build()
        );
    }
}
