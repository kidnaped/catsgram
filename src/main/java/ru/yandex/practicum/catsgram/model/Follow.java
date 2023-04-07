package ru.yandex.practicum.catsgram.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Follow {
    private Integer id;
    private String authorId;
    private String followerId;

    public Follow(String  authorId, String followerId) {
        this.authorId = authorId;
        this.followerId = followerId;
    }
}
