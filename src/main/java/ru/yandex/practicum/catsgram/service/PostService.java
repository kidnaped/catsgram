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

@Service
public class PostService {
    private final List<Post> posts = new ArrayList<>();
    private final UserService userService;
    private Integer globalId = 0;

    @Autowired
    public PostService(UserService userService) {
        this.userService = userService;
    }

    public List<Post> findAll(String sort, int from, int size) {
        return posts.stream()
                .sorted((p0, p1) -> {
                    int comparator = p0.getCreationDate().compareTo(p1.getCreationDate());
                    if (sort.equals("desc")) {
                        comparator = comparator * -1;
                    }
                    return comparator;})
                .skip(from)
                .limit(size)
                .collect(Collectors.toList());
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

    public List<Post> searchPosts(String author, LocalDate date) {
        return posts.stream()
                .filter(post -> post.getAuthor().equals(author))
                .filter(post -> LocalDate.ofInstant(post.getCreationDate(), ZoneId.of("GMT+3")).equals(date))
                .collect(Collectors.toList());
    }

    public List<Post> findPostsByEmail(String sort, int size, String friendEmail) {
        return posts.stream()
                .filter(post -> post.getAuthor().equals(friendEmail))
                .sorted((p0, p1) -> {
                    int comparator = p0.getCreationDate().compareTo(p1.getCreationDate());
                    if (sort.equals("desc")) {
                        comparator = comparator * -1;
                    }
                    return comparator;})
                .limit(size)
                .collect(Collectors.toList());
    }

    private Integer generateId() {
        return ++globalId;
    }
}
