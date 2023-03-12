package ru.yandex.practicum.catsgram.model;

import lombok.Data;

import java.util.List;

@Data
public class Friends {
    private String sort;
    private int size;
    private List<String> friendsEmail;
}
