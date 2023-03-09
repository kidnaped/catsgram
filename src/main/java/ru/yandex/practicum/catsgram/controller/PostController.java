package ru.yandex.practicum.catsgram.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.zalando.logbook.Logbook;
import org.zalando.logbook.LogbookCreator;
import org.zalando.logbook.httpclient.LogbookHttpResponseInterceptor;
import ru.yandex.practicum.catsgram.model.Post;
import java.util.ArrayList;
import java.util.List;

@RestController
public class PostController {
    private final static Logger log = LoggerFactory.getLogger(PostController.class);
    private final List<Post> posts = new ArrayList<>();

    @GetMapping("/posts")
    public List<Post> findAll() {
        log.debug("Текущее количество постов: {}", posts.size());
        return posts;
    }

    @PostMapping(value = "/post")
    public void create(@RequestBody Post post) {
        log.debug(String.valueOf(post));
        posts.add(post);
    }
}