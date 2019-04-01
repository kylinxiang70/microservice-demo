package org.kylin.userservice.controller;

import org.kylin.userservice.entity.Filter;
import org.kylin.userservice.entity.User;
import org.kylin.userservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping
    public User saveUser(@RequestBody User user) {
        return userService.save(user);
    }

    @PostMapping("/filter")
    public List<User> getUsersByFilter(@RequestBody List<Filter> filters) {
        return userService.queryByFilter(filters);
    }
}
