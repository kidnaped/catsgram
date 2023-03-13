package ru.yandex.practicum.catsgram.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.catsgram.exception.IncorrectParameterException;
import ru.yandex.practicum.catsgram.model.Post;
import ru.yandex.practicum.catsgram.service.PostService;

import java.time.LocalDate;
import java.util.List;

import static ru.yandex.practicum.catsgram.Constants.*;

@RestController
@RequestMapping("/posts")
public class PostController {
    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    public List<Post> findAll(@RequestParam(defaultValue = DESCENDING_ORDER, required = false) String sort,
                              @RequestParam(defaultValue = "1", required = false) Integer page,
                              @RequestParam(defaultValue = "10", required = false) Integer size) {
        if (!SORTS.contains(sort)) {
            throw new IncorrectParameterException("sort");
        }
        if (page <= 0) {
            throw new IncorrectParameterException("page");
        }
        if (size <= 0) {
            throw new IncorrectParameterException("size");
        }


        Integer from = (page - 1) * size;
        return postService.findAll(sort, from, size);
    }

    @GetMapping("/{postId}")
    public Post findById(@PathVariable int postId) {
        return postService.findById(postId);
    }

    @GetMapping("/search")
    public List<Post> findByAuthorAndDate(@RequestParam String author,
                                  @RequestParam LocalDate date) {
        return postService.findByAuthorAndDate(author, date);
    }

    @PostMapping
    public Post create(@RequestBody Post post) {
        return postService.create(post);
    }

}