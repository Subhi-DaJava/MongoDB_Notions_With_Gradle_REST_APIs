package com.oc.blogdatalayer.controller;

import com.oc.blogdatalayer.BlogDataLayerApplication;
import com.oc.blogdatalayer.exception.PostNotFoundException;
import com.oc.blogdatalayer.model.Comment;
import com.oc.blogdatalayer.model.LightPost;
import com.oc.blogdatalayer.model.Post;
import com.oc.blogdatalayer.model.PostAggregate;
import com.oc.blogdatalayer.repository.PostRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;

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

    @GetMapping("/posts-by-name-with-date-desc")
    List<String> getPostNameByOrderByDateDesc() {
        List<LightPost> postList = postRepository.findByOrderByDateDesc();
        return postList.stream().map(LightPost::getName).toList();
    }

    @GetMapping("/posts-by-id-and-name-orderby-date")
    Map<String, String> findPostByIdAndNameOrderByDate() {
        List<Post> posts = postRepository.findByIdAndNameExcludeOthers();
        Map<String, String> postsByIdAndName = new HashMap<>();
        posts.forEach(post -> postsByIdAndName.put(post.getId(), post.getName()));
        return postsByIdAndName;
    }

    @GetMapping("/retrieve-all-names-by-aggregation")
    List<String> retrieveAllNames() {
        return postRepository.findAllNames();
    }

    @GetMapping("/group-posts-by-date")
    List<PostAggregate> groupPostsByDate() {
        return postRepository.groupPostsByDate();
    }

    @PostMapping("/add-post")
    public Post savePost(@RequestBody Post postToSaved) {
        Post newPost = new Post();
        newPost.setName(postToSaved.getName());
        newPost.setContent(postToSaved.getContent());
        newPost.setDate(LocalDateTime.now());
        newPost.setTag(postToSaved.getTag());
        logger.info("NewPost has been successfully saved in DB!");
        return postRepository.insert(newPost);
    }

    @PostMapping("/add-posts")
    public List<Post> savePosts(@RequestBody List<Post> posts) {
        List<Post> postListSaved = new ArrayList<>(posts);
        postListSaved.forEach(post -> post.setDate(LocalDateTime.now()));
        return postRepository.insert(postListSaved);
    }

    @PutMapping("/update-post")
    ResponseEntity<Post> updateExistingPost(@RequestParam String name, @RequestBody Post postToUpdate) {
        Optional<Post> postExisting = Optional.ofNullable(postRepository.findByName(name));
        if (postExisting.isPresent()) {
            postExisting.get().setName(postToUpdate.getName());
            postExisting.get().setTag(postToUpdate.getTag());
            postExisting.get().setContent(postToUpdate.getContent());

            return ResponseEntity.ok(postRepository.save(postExisting.get()));
        } else {
            throw new PostNotFoundException("Any Post found with the name:{%s}".formatted(name));
        }
    }

    @PutMapping("/add-comment")
    ResponseEntity<Post> addComment(@RequestBody Comment newComment, @RequestParam String name) {
        Optional<Post> existingPost = Optional.ofNullable(postRepository.findByName(name));

        if (existingPost.isPresent()) {
            existingPost.get().getComments().add(newComment);
            return ResponseEntity.ok(postRepository.save(existingPost.get()));
        } else {
            throw new PostNotFoundException("Post not found with this name:{%s} in DB!".formatted(name));
        }
    }

    @DeleteMapping("/delete-by-name/{name}")
    void deleteByName(@PathVariable String name) {
        Optional<Post> existingPost = Optional.ofNullable(postRepository.findByName(name));

        if (existingPost.isPresent()) {
            postRepository.deleteByName(name);
        } else {
            logger.info("Post not found with this name:{%s} id DB!".formatted(name));
            throw new PostNotFoundException("Post not found with this name:{%s} id DB!".formatted(name));
        }
    }
    @DeleteMapping("/delete-by-id/{id}")
    void deleteById(@PathVariable String id) {
        Optional<Post> existingPost = postRepository.findById(id);

        if (existingPost.isPresent()) {

            postRepository.deleteById(id);
        } else {
            logger.info("Post not found with this name:{%s} id DB!".formatted(id));
            throw new PostNotFoundException("Post not found with this name:{%s} id DB!".formatted(id));
        }
    }

}
