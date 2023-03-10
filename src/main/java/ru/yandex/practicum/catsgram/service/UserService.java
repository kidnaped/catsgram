package ru.yandex.practicum.catsgram.service;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import ru.yandex.practicum.catsgram.exception.InvalidEmailException;
import ru.yandex.practicum.catsgram.exception.UserAlreadyExistException;
import ru.yandex.practicum.catsgram.model.User;

import java.util.*;

@Service
public class UserService {
    private final Map<String, User> users = new HashMap<>();

    public List<User> findAll() {
        return new ArrayList<>(users.values());
    }

    public Optional<User> findByEmail(String email) {
        return users.values().stream()
                .filter(u -> u.getEmail().equals(email))
                .findFirst();
    }

    public User create(User user) {
        try {
            if (users.containsKey(user.getEmail())) {
                throw new UserAlreadyExistException("User is already exist");
            }
            users.put(user.getEmail(), user);
        } catch (UserAlreadyExistException | InvalidEmailException e) {
            System.out.println(e.getMessage());
        }

        return user;
    }

    public User put(User user) {
        try {
            users.put(user.getEmail(), user);
        } catch (InvalidEmailException e) {
            System.out.println(e.getMessage());
        }

        return user;
    }
}
