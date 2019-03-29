package org.kylin.authcenter.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.kylin.authcenter.entity.User;
import org.kylin.authcenter.exception.UserNotFoundException;
import org.kylin.authcenter.repository.UserRepository;
import org.kylin.authcenter.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public User save(User user) {
        checkUserInfo(user);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public User update(User user) {
        if (null == user.getId() || null == userRepository.getOne(user.getId())) {
            throw new UserNotFoundException("User not exists!");
        }
        return userRepository.save(user);
    }

    @Override
    public void deleteByUsername(String username) {
        userRepository.deleteByUsername(username);
    }

    @Override
    public User getUserByName(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException("User not found: " +
                username));
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(String id) {
        return userRepository.getOne(id);
    }

    private void checkUserInfo(User user) {
        List<String> infos = new ArrayList<>();

        if (null == user.getUsername() || "".equals(user.getUsername())) {
            infos.add("Username cannot be empty. ");
        }

        if (null == user.getPassword()) {
            infos.add("Password cannot be empty");
        } else if (user.getPassword().length() < 6) {
            infos.add("Passwords must contain at least six characters");
        }

        if (null == user.getRoles() || user.getRoles().isEmpty()) {
            infos.add("Roles cannot be empty");
        }

        if (!infos.isEmpty()) {
            log.error(infos.toString());
            throw new IllegalArgumentException(infos.toString());
        }

    }
}
