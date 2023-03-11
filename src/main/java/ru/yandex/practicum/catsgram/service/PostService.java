package ru.yandex.practicum.catsgram.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.catsgram.exception.PostNotFoundException;
import ru.yandex.practicum.catsgram.exception.UserNotFoundException;
import ru.yandex.practicum.catsgram.model.Post;
import ru.yandex.practicum.catsgram.model.User;

import java.util.ArrayList;
import java.util.List;

@Service
public class PostService {
    private final List<Post> posts = new ArrayList<>();
    private final UserService userService;
    private Integer globalId = 0;

    @Autowired
    public PostService(UserService userService) {
        this.userService = userService;
    }

    public List<Post> findAll() {
        return posts;
    }

    public Post findById(int postId) {
        return posts.stream()
                .filter(post -> post.getId() == postId)
                .findFirst()
                .orElseThrow(() -> new PostNotFoundException(String.format("Post № # %d not found", postId)));
    }

    public Post create(Post post) {
        try {
            User user = userService.findByEmail(post.getAuthor());
            if (user == null) {
                throw new UserNotFoundException(String.format("User %s not found", post.getAuthor()));
            }
            post.setId(generateId());
            posts.add(post);
        } catch (UserNotFoundException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return post;
    }

    private Integer generateId() {
        return ++globalId;
    }
}
