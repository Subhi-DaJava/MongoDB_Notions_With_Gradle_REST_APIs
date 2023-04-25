package com.oc.blogdatalayer.controller;

import com.oc.blogdatalayer.BlogDataLayerApplication;
import com.oc.blogdatalayer.exception.PostNotFoundException;
import com.oc.blogdatalayer.exception.TutorialNotFoundException;
import com.oc.blogdatalayer.model.Tutorial;
import com.oc.blogdatalayer.repository.TutorialRepository;
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
@RequestMapping("/api/tutorials/v1")
public class TutorialController {
    private static final Logger logger = LoggerFactory.getLogger(BlogDataLayerApplication.class);
    private final TutorialRepository tutorialRepository;

    public TutorialController(TutorialRepository tutorialRepository) {
        this.tutorialRepository = tutorialRepository;
    }

    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Tutorial> getAllTutorials() {
        return tutorialRepository.findAll();
    }
    @GetMapping(value = "/content/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getContentById(@PathVariable String id) {
        Optional<Tutorial> tutorial = tutorialRepository.findById(id);

        if(tutorial.isPresent()) {
            logger.info("Content: " + tutorial.get().getContent());
            return tutorial.get().getContent();
        } else {
            logger.info("Tutorial not found");
            throw new TutorialNotFoundException("No Tutorial with this id: {%s}".formatted(id));
        }
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Tutorial getPostById(@PathVariable String id) {
        Optional<Tutorial> tutorial = tutorialRepository.findById(id);

        if(tutorial.isPresent()) {
            return tutorial.get();
        } else {
            throw new PostNotFoundException("No Tutorial with this id: {%s}".formatted(id));
        }
    }
}
