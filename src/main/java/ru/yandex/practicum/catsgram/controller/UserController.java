package ru.yandex.practicum.catsgram.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.catsgram.model.User;
import ru.yandex.practicum.catsgram.model.UserAlreadyExistException;

import java.util.ArrayList;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/users")
public class UserController {
    private List<User> users = new ArrayList<>();

    @GetMapping
    public List<User> getUsers() {
        log.debug("Users total: {}", users.size());
        return users;
    }

    @PostMapping
    public User addNewUser(@RequestBody User user) {
        try {
            if (users.contains(user)) {
                throw new UserAlreadyExistException("User is already exist");
            }
            if (user.getEmail() == null) {
                throw new InvalidEmailException("Email is either not entered or in wrong format");
            }
            users.add(user);
        } catch (UserAlreadyExistException | InvalidEmailException e) {
            System.out.println(e.getMessage());
        }
        log.debug(String.valueOf(user));
        return user;
    }

    @PutMapping
    public User updateOrAddUser(@RequestBody User user) {
        try {
            if (user.getEmail() == null) {
                throw new InvalidEmailException("Email is either not entered or in wrong format");
            }
            if (users.contains(user)) {
                users.set(users.indexOf(user), user);
            } else {
                users.add(user);
            }
        } catch (InvalidEmailException e) {
            System.out.println(e.getMessage());
        }
        log.debug(String.valueOf(user));
        return user;
    }
}
