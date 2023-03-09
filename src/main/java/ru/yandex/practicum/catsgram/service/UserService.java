package ru.yandex.practicum.catsgram.service;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.catsgram.exception.InvalidEmailException;
import ru.yandex.practicum.catsgram.exception.UserAlreadyExistException;
import ru.yandex.practicum.catsgram.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserService {
    private final Map<String, User> users = new HashMap<>();

    public List<User> findAll() {
        return new ArrayList<>(users.values());
    }

    public User findByEmail(String email) {
        if (users.containsKey(email)) {
            return users.get(email);
        }
        return null;
    }

    public User create(User user) {
        try {
            if (users.containsKey(user.getEmail())) {
                throw new UserAlreadyExistException("User is already exist");
            }
            emailValidation(user);
            users.put(user.getEmail(), user);
        } catch (UserAlreadyExistException | InvalidEmailException e) {
            System.out.println(e.getMessage());
        }

        return user;
    }

    public User put(User user) {
        try {
            emailValidation(user);
            users.put(user.getEmail(), user);
        } catch (InvalidEmailException e) {
            System.out.println(e.getMessage());
        }

        return user;
    }

    private void emailValidation(User user) throws InvalidEmailException {
        if (user.getEmail() == null || user.getEmail().isBlank()) {
            throw new InvalidEmailException("Email is either not entered or in wrong format");
        }
    }
}
