package ru.yandex.practicum.catsgram.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.catsgram.exception.IncorrectParameterException;
import ru.yandex.practicum.catsgram.model.FeedParams;
import ru.yandex.practicum.catsgram.model.Post;
import ru.yandex.practicum.catsgram.service.PostService;

import java.util.ArrayList;
import java.util.List;

import static ru.yandex.practicum.catsgram.Constants.*;

@RestController
public class PostFeedController {
    private final PostService postService;

    public PostFeedController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping("/feed/friends")
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
            friendsFeed.addAll(postService.findPostsByEmail(feedParams.getSort(), feedParams.getSize(), email));
        }
        return friendsFeed;
    }
}