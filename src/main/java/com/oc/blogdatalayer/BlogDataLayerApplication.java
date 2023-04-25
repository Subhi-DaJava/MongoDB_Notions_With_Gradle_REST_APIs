package com.oc.blogdatalayer;

import com.oc.blogdatalayer.model.Post;
import com.oc.blogdatalayer.model.Tutorial;
import com.oc.blogdatalayer.repository.PostRepository;
import com.oc.blogdatalayer.repository.TutorialRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Optional;

@SpringBootApplication
public class BlogDataLayerApplication implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(BlogDataLayerApplication.class);

    @Autowired
    private PostRepository postRepository;
    @Autowired
    private TutorialRepository tutorialRepository;

    public static void main(String[] args) {
        SpringApplication.run(BlogDataLayerApplication.class, args);
    }


    @Override
    public void run(String... args) {
        logger.info("Method <run> from BlogDataLayerApplication starts !");
        Optional<Post> post = postRepository.findById("6177a31824f1d205e0b0692d");
        if (post.isPresent()) {
            logger.info("Content: " + post.get().getContent());
        } else {
            logger.info("Post not found");
        }

        Optional<Tutorial> tutorial = tutorialRepository.findById("6192c22d783f4a2a0a7d9bf3");
        if (tutorial.isPresent()) {
            logger.info("Content: " + tutorial.get().getContent());
        } else {
            logger.info("Tutorial not found");
        }
    }
}
