package ru.yandex.practicum.catsgram.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.catsgram.dao.PostDao;
import ru.yandex.practicum.catsgram.exception.UserNotFoundException;
import ru.yandex.practicum.catsgram.model.Post;
import ru.yandex.practicum.catsgram.model.User;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class PostService {
    private final PostDao postDao;
    private final UserService userService;

    @Autowired
    public PostService(UserService userService, PostDao postDao) {
        this.userService = userService;
        this.postDao = postDao;
    }

    public Collection<Post> findPostsByUser(String userId) {
        User user = userService.findUserById(userId).orElseThrow(
                () -> new UserNotFoundException("User ID " + userId + " not found."));

        return postDao.findPostsByUser(user);
    }

    public Collection<Post> findPostsByUser(String userId, Integer size, String sort) {
        return findPostsByUser(userId)
                .stream()
                .sorted((p1, p2) -> {
                    int comp = p1.getCreationDate().compareTo(p2.getCreationDate());
                    if (sort.equals("desc")) {
                        comp = -comp;
                    }
                    return comp;
                })
                .limit(size)
                .collect(Collectors.toList());
    }
}
