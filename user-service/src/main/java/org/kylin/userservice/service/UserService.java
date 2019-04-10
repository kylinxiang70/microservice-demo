package org.kylin.userservice.service;

import org.kylin.userservice.dto.Filter;
import org.kylin.userservice.dto.UserDto;
import org.kylin.userservice.entity.User;

import java.util.List;

public interface UserService {
    List<User> getAllUsers();

    User save(UserDto userDto);

    List<User> queryByFilter(List<Filter> filters);
}
