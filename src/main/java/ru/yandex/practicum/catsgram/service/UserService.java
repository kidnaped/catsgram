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

    public User findByEmail(String email) {
        if (email == null) {
            return null;
        } else {
            return users.get(email);
        }
    }

    public User create(User user) {
        try {
            emailValidation(user.getEmail());
            if (users.containsKey(user.getEmail())) {
                throw new UserAlreadyExistException("User is already exist");
            }
            users.put(user.getEmail(), user);
        } catch (UserAlreadyExistException | InvalidEmailException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return user;
    }

    public User update(User user) {
        try {
            emailValidation(user.getEmail());
            users.put(user.getEmail(), user);
        } catch (InvalidEmailException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return user;
    }

    private void emailValidation(String email) throws InvalidEmailException {
        if (email == null || email.isBlank()) {
            throw new InvalidEmailException("Email can not be empty");
        }
    }
}
