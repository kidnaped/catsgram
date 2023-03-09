package ru.yandex.practicum.catsgram.controller;

public class InvalidEmailException extends Exception {
    public InvalidEmailException(String message) {
        super(message);
    }
}
