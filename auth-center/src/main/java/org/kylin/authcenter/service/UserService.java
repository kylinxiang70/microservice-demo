package org.kylin.authcenter.service;

import org.kylin.authcenter.dto.AuthDto;
import org.kylin.authcenter.dto.Filter;
import org.kylin.authcenter.entity.User;

import java.util.List;

public interface UserService {
    User save(User user);

    void deleteByUsername(String username);

    void deleteById(String id);

    List<User> getAll();

    User getUserById(String id);

    User createDefaultAuthUser(AuthDto dto);

    List<User> getUsersByFilter(List<Filter> filters);
}
