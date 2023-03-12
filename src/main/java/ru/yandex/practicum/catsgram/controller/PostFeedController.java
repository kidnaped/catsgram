package ru.yandex.practicum.catsgram.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.catsgram.model.Friends;
import ru.yandex.practicum.catsgram.model.Post;
import ru.yandex.practicum.catsgram.service.PostService;

import java.util.ArrayList;
import java.util.List;

@RestController
public class PostFeedController {
    private final PostService postService;

    public PostFeedController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping("/feed/friends")
    public List<Post> friendsPosts(@RequestBody String requestJson) {
        ObjectMapper mapper = new ObjectMapper();
        Friends friends;
        try {
            String finalJson = mapper.readValue(requestJson, String.class);
            friends = mapper.readValue(finalJson, Friends.class);

            if (friends != null) {
                List<Post> friendsPosts = new ArrayList<>();
                for (String friendEmail : friends.getFriendsEmail()) {
                    friendsPosts.addAll(postService.findPostsByEmail(friends.getSort(), friends.getSize(), friendEmail));
                }

                return friendsPosts;
            } else {
                throw new RuntimeException("Incorrectly filled parameters for request.");
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Wrong Json format", e);
        }
    }
}