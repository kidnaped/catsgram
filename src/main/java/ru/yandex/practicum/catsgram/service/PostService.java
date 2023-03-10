package ru.yandex.practicum.catsgram.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.catsgram.exception.UserNotFoundException;
import ru.yandex.practicum.catsgram.model.Post;
import ru.yandex.practicum.catsgram.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PostService {
    private final List<Post> posts = new ArrayList<>();
    private final UserService userService;

    @Autowired
    public PostService(UserService userService) {
        this.userService = userService;
    }

    public List<Post> findAll() {
        return posts;
    }

    public Optional<Post> findById(int postId) {
        return posts.stream()
                .filter(post -> post.getId() == postId)
                .findFirst();
    }

    public Post create(Post post) {
        try {
            Optional<User> user = userService.findByEmail(post.getAuthor());
            if (user.isEmpty()) {
                throw new UserNotFoundException("Пользователь " + post.getAuthor() + " не найден");
            }
            posts.add(post);
        } catch (UserNotFoundException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return post;
    }
}
