package org.kylin.authcenter.controller;

import org.kylin.authcenter.dto.AuthDto;
import org.kylin.authcenter.entity.Filter;
import org.kylin.authcenter.entity.User;
import org.kylin.authcenter.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/users")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Createt new user
     *
     * @param user
     * @return
     */
    @PostMapping
    public ResponseEntity<User> save(@RequestBody User user) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.save(user));
    }

    /**
     * Get users by filter
     *
     * @param filters
     * @return
     */
    @PostMapping("/filter")
    public ResponseEntity<List<User>> getUserByFilter(@RequestBody List<Filter> filters) {
        return ok().body(userService.getUsersByFilter(filters));
    }

    /**
     * Get all users
     *
     * @return
     */
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return ok().body(userService.getAll());
    }

    /**
     * Query user by id
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable String id) {
        return ok().body(userService.getUserById(id));
    }

    /**
     * Delete user by id
     *
     * @param id
     */
    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable String id) {
        userService.deleteById(id);
    }


    /**
     * synchronize user info between user-service and auth-center
     *
     * @param dto id username password
     * @return created info
     */
    @PostMapping("/auth")
    public ResponseEntity<Void> createDefaultUser(@RequestBody AuthDto dto) {
        userService.createDefaultAuthUser(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
