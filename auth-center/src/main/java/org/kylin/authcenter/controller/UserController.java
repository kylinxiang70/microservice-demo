package org.kylin.authcenter.controller;

import org.kylin.authcenter.dto.AuthDto;
import org.kylin.authcenter.entity.User;
import org.kylin.authcenter.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.ResponseEntity.noContent;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/users")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<User> save(@RequestBody User user) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.save(user));
    }

    @DeleteMapping("/filter")
    public ResponseEntity<Void> deleteByUsername(@RequestParam(value = "username") String username) {
        userService.deleteByUsername(username);
        return noContent().build();
    }

    @PutMapping
    public ResponseEntity<User> update(@RequestBody User user) {
        return ok().body(userService.update(user));
    }

    @GetMapping("/filter")
    public ResponseEntity<User> getUserByUsername(@RequestParam("username") String username) {
        return ok().body(userService.getUserByName(username));
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return ok().body(userService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable String id) {
        return ok().body(userService.getUserById(id));
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable String id) {
        userService.deleteById(id);
    }

    @PostMapping("/auth")
    public ResponseEntity<Void> createDefaultUser(@RequestBody AuthDto dto) {
        userService.createDefaultAuthUser(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
