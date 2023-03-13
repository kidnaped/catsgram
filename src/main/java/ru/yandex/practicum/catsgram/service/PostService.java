package ru.yandex.practicum.catsgram.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.catsgram.exception.PostNotFoundException;
import ru.yandex.practicum.catsgram.exception.UserNotFoundException;
import ru.yandex.practicum.catsgram.model.Post;
import ru.yandex.practicum.catsgram.model.User;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static ru.yandex.practicum.catsgram.Constants.*;

@Service
public class PostService {
    private final List<Post> posts = new ArrayList<>();
    private final UserService userService;
    private Integer globalId = 0;

    @Autowired
    public PostService(UserService userService) {
        this.userService = userService;
    }

    public Post create(Post post) {
        User postAuthor = userService.findByEmail(post.getAuthor());
        if (postAuthor == null) {
            throw new UserNotFoundException(String.format("User %s not found", post.getAuthor()));
        }
        post.setId(generateId());
        posts.add(post);
        return post;
    }

    public Post findById(Integer postId) {
        return posts.stream()
                .filter(post -> post.getId().equals(postId))
                .findFirst()
                .orElseThrow(() -> new PostNotFoundException(String.format("Post № # %d not found", postId)));
    }

    public List<Post> findAll(String sort, Integer from, Integer size) {
        return posts.stream()
                .sorted((p0, p1) -> compare(p0, p1, sort))
                .skip(from)
                .limit(size)
                .collect(Collectors.toList());
    }

    public List<Post> findPostsByEmail(String sort, int size, String friendEmail) {
        return posts.stream()
                .filter(post -> post.getAuthor().equals(friendEmail))
                .sorted((p0, p1) -> compare(p0, p1, sort))
                .limit(size)
                .collect(Collectors.toList());
    }

    public List<Post> findByAuthorAndDate(String author, LocalDate date) {
        return posts.stream()
                .filter(post -> post.getAuthor().equals(author))
                .filter(post -> LocalDate.ofInstant(post.getCreationDate(), ZoneId.of("GMT+3")).equals(date))
                .collect(Collectors.toList());
    }

    private Integer generateId() {
        return ++globalId;
    }

    private int compare(Post p0, Post p1, String sort) {
        int result = p0.getCreationDate().compareTo(p1.getCreationDate());
        if (sort.equals(DESCENDING_ORDER)) {
            result = -result;
        }
        return result;
    }
}
