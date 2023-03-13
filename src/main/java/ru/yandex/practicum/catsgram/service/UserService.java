package ru.yandex.practicum.catsgram.service;

import org.springframework.stereotype.Service;
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

    public User create(User user) {
        emailValidation(user.getEmail());
        if (users.containsKey(user.getEmail())) {
            throw new UserAlreadyExistException(String.format("User %s is already exist", user.getEmail()));
        }
        users.put(user.getEmail(), user);
        return user;
    }

    public User update(User user) {
        emailValidation(user.getEmail());
        users.put(user.getEmail(), user);

        return user;
    }

    public User findByEmail(String email) {
        if (email == null) {
            return null;
        } else {
            return users.get(email);
        }
    }

    private void emailValidation(String email) {
        if (email == null || email.isBlank()) {
            throw new InvalidEmailException("Email can not be empty");
        }
    }
}
