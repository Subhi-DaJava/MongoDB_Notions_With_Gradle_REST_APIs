package com.oc.blogdatalayer.controller;

import com.oc.blogdatalayer.BlogDataLayerApplication;
import com.oc.blogdatalayer.exception.PostNotFoundException;
import com.oc.blogdatalayer.model.Post;
import com.oc.blogdatalayer.repository.PostRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/posts/v1")
public class PostController {
    private static final Logger logger = LoggerFactory.getLogger(BlogDataLayerApplication.class);

    private final PostRepository postRepository;

    public PostController(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    @GetMapping(value = "/content/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getContentById(@PathVariable String id) {
        Optional<Post> post = postRepository.findById(id);

        if (post.isPresent()) {
            logger.info("Content: " + post.get().getContent());
            return post.get().getContent();
        } else {
            logger.info("Post not found");
            throw new PostNotFoundException("No post with this id: {%s}".formatted(id));
        }
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Post getPostById(@PathVariable String id) {
        Optional<Post> post = postRepository.findById(id);

        if (post.isPresent()) {
            return post.get();
        } else {
            throw new PostNotFoundException("No post with this id: {%s}".formatted(id));
        }
    }

    @GetMapping("/post-by-name/{postName}")
    Post getPostsByName(@PathVariable String postName) {
        return postRepository.findByName(postName);
    }
    @GetMapping("/contain/{givenWord}")
    List<Post> findByContentContainingWithGivenWord(@PathVariable String givenWord) {
        return postRepository.findByContentContaining(givenWord);
    }
}
