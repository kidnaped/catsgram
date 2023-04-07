package ru.yandex.practicum.catsgram.controller;

import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.catsgram.exception.IncorrectParameterException;
import ru.yandex.practicum.catsgram.model.FeedParams;
import ru.yandex.practicum.catsgram.model.Post;
import ru.yandex.practicum.catsgram.service.FeedService;
import ru.yandex.practicum.catsgram.service.PostService;

import java.util.ArrayList;
import java.util.List;

import static ru.yandex.practicum.catsgram.Constants.*;

@RestController
@RequestMapping("/feed")
public class PostFeedController {
    //TODO remove from controller
    private final PostService postService;
    private final FeedService feedService;

    public PostFeedController(PostService postService, FeedService feedService) {
        this.postService = postService;
        this.feedService = feedService;
    }

    //TODO remove duplicate functionality
    @PostMapping("/friends")
    public List<Post> getFriendsFeed(@RequestBody FeedParams feedParams) {
        if (!SORTS.contains(feedParams.getSort())) {
            throw new IncorrectParameterException("feed sort");
        }
        if (feedParams.getFriendsEmail().isEmpty()) {
            throw new IncorrectParameterException("feed friends email");
        }
        if (feedParams.getSize() == null || feedParams.getSize() <= 0) {
            throw new IncorrectParameterException("feed size");
        }

        List<Post> friendsFeed = new ArrayList<>();
        for (String email : feedParams.getFriendsEmail()) {
            friendsFeed.addAll(postService.findPostsByUser(feedParams.getSort(), feedParams.getSize(), email));
        }
        return friendsFeed;
    }

    @GetMapping
    public List<Post> getFriendsFeed(@RequestParam("userId") String userId,
                                     @RequestParam(defaultValue = "10") int max) {
        return feedService.getFeedFor(userId, max);
    }
}