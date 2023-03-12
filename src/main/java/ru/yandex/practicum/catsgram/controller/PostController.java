package ru.yandex.practicum.catsgram.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.catsgram.model.Post;
import ru.yandex.practicum.catsgram.service.PostService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/posts")
public class PostController {
    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    public List<Post> findAll(@RequestParam(defaultValue = "desc") String sort,
                              @RequestParam(defaultValue = "1") int page,
                              @RequestParam(defaultValue = "10") int size) {
        if (!sort.equals("asc") || !sort.equals("desc")) {
            throw new IllegalArgumentException("Must use \"asc\" or \"desc\" values for \"sort\" parameter.");
        }
        if (page <= 0 || size <= 0) {
            throw new IllegalArgumentException("Page or size must be positive.");
        }

        int from = (page - 1) * size;

        return postService.findAll(sort, from, size);
    }

    @GetMapping("/{postId}")
    public Post findById(@PathVariable int postId) {
        return postService.findById(postId);
    }

    @GetMapping("/search")
    public List<Post> searchPosts(@RequestParam String author,
                                  @RequestParam LocalDate date) {
        return postService.searchPosts(author, date);
    }

    @PostMapping
    public Post create(@RequestBody Post post) {
        return postService.create(post);
    }

}