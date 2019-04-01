package org.kylin.userservice.entity.service;

import org.kylin.userservice.entity.entity.User;

import java.util.List;

public interface UserService {
    List<User> getAllUsers();

    User save(User user);
}
