package ru.yandex.practicum.catsgram.controller;

import ru.yandex.practicum.catsgram.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.catsgram.model.User;

import java.util.Collection;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public Collection<User> getAllUsers() {
        return this.userService.allUsers();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addNewUsers(@RequestBody User user) {
        this.userService.createNewUser(user);
    }

    @PutMapping
    public void update(@RequestBody User user) {
        this.userService.update(user);
    }

    @GetMapping("/users/{id}")
    public User getUserById(@PathVariable("id") Long userId) {
        return userService.getUserById(userId);
    }
}
