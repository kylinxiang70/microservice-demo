package org.kylin.userservice.service;

import org.kylin.userservice.dto.Filter;
import org.kylin.userservice.entity.User;

import java.util.List;

public interface UserService {
    List<User> getAllUsers();

    User save(User user);

    List<User> queryByFilter(List<Filter> filters);
}
