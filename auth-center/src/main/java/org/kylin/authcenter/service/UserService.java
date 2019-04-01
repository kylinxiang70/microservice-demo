package org.kylin.authcenter.service;

import org.kylin.authcenter.entity.User;

import java.util.List;

public interface UserService {
    User save(User user);

    User update(User user);

    void deleteByUsername(String username);

    void deleteById(String id);

    User getUserByName(String username);

    List<User> getAll();

    User getUserById(String id);
}
