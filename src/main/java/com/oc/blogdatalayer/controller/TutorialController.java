package com.oc.blogdatalayer.controller;

import com.oc.blogdatalayer.BlogDataLayerApplication;
import com.oc.blogdatalayer.exception.PostNotFoundException;
import com.oc.blogdatalayer.exception.TutorialNotFoundException;
import com.oc.blogdatalayer.model.NameAndDescriptionTuto;
import com.oc.blogdatalayer.model.TutoAggregate;
import com.oc.blogdatalayer.model.Tutorial;
import com.oc.blogdatalayer.repository.TutorialRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.*;

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

        if (tutorial.isPresent()) {
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

        if (tutorial.isPresent()) {
            return tutorial.get();
        } else {
            throw new PostNotFoundException("No Tutorial with this id: {%s}".formatted(id));
        }
    }

    @GetMapping("/tutorial-by-name/{tutorialName}")
    Tutorial getTutorialByName(@PathVariable String tutorialName) {
        return tutorialRepository.findByName(tutorialName);
    }

    @GetMapping("/tutos-by-short-desc-contain/{givenWord}")
    List<Tutorial> getTutosByShortDescContainingWithGivenWord(@PathVariable String givenWord) {
        return tutorialRepository.findByShortDescriptionContaining(givenWord);
    }

    @GetMapping("/tutos-by-name-and-description")
    public List<NameAndDescriptionTuto> getTutosByNameAndDescription() {
        return tutorialRepository.findByOrderByNameAsc();
    }

    @GetMapping("/tutos-by-name-and-description-with-map")
    public Map<String, String> getTutosByNameAndDescriptioWithMap() {
        List<NameAndDescriptionTuto> tutos = tutorialRepository.findByOrderByNameAsc();
        Map<String, String> tutosNameAndShortDescription = new HashMap<>();
        tutos.forEach(tuto -> tutosNameAndShortDescription.put(tuto.getName(), tuto.getShortDescription()));
        return tutosNameAndShortDescription;
    }

    @GetMapping("/tutos-by-id-and-name")
    public List<Tutorial> findByIdAndName() {
        return tutorialRepository.findIdAndNameExcludeOthers();
    }

    @GetMapping("/categories-by-aggregation")
    public List<String> retrieveAllCategories() {
        return tutorialRepository.findAllCategories();
    }

    @GetMapping("/aggregate-names-and-short-description-by-category")
    List<TutoAggregate> aggregateNamesAndShortDescriptionByCategory() {
        return tutorialRepository.groupByCategoryAggregateNamesAndShortDescription();
    }

    @PostMapping("/add-tuto")
    public ResponseEntity<Tutorial> saveTuto(@RequestBody Tutorial tutorialToSaved) {
        Tutorial tutorialSaved = new Tutorial();
        tutorialSaved.setCategory(tutorialToSaved.getCategory());
        tutorialSaved.setName(tutorialToSaved.getName());
        tutorialSaved.setContent(tutorialToSaved.getContent());
        tutorialSaved.setShortDescription(tutorialToSaved.getShortDescription());
        tutorialSaved.setTag(tutorialToSaved.getTag());
        URI location =
                ServletUriComponentsBuilder
                        .fromCurrentServletMapping()
                        .path("/{id}")
                        .build()
                        .expand(tutorialSaved.getId()).toUri();

        return ResponseEntity.created(location).body(tutorialRepository.insert(tutorialSaved));
    }

    @PostMapping("/add-tutos")
    public ResponseEntity<List<Tutorial>> saveTuto(@RequestBody List<Tutorial> tutorials) {
        List<Tutorial> tutorialListSaved = new ArrayList<>(tutorials);
        List<Tutorial> tutorialList = tutorialRepository.insert(tutorialListSaved);
        return ResponseEntity.ok(tutorialList);
    }

    @PutMapping("/update-tuto")
    public ResponseEntity<Tutorial> updateTuto(@RequestBody Tutorial tutorialToUpdate) {
        Optional<Tutorial> tutorialUpdated = Optional.ofNullable(tutorialRepository.findByName(tutorialToUpdate.getName()));
        if (tutorialUpdated.isPresent()) {
            tutorialUpdated.get().setName(tutorialToUpdate.getName());
            tutorialUpdated.get().setCategory(tutorialToUpdate.getCategory());
            tutorialUpdated.get().setTag(tutorialToUpdate.getTag());
            tutorialUpdated.get().setContent(tutorialToUpdate.getContent());
            tutorialUpdated.get().setShortDescription(tutorialToUpdate.getShortDescription());
            return ResponseEntity.ok(tutorialRepository.save(tutorialUpdated.get()));
        } else {
            throw new TutorialNotFoundException("This Tutorial with name:{%s} doesn't exist in DB".formatted(tutorialToUpdate.getName()));
        }
    }

    @PutMapping("/update-by-name")
    public ResponseEntity<Tutorial> updateTutoByName(@RequestParam String name, @RequestBody Tutorial tutorialToUpdate) {
        Optional<Tutorial> tutorialUpdated = Optional.ofNullable(tutorialRepository.findByName(name));
        if (tutorialUpdated.isPresent()) {
            tutorialUpdated.get().setName(tutorialToUpdate.getName());
            tutorialUpdated.get().setCategory(tutorialToUpdate.getCategory());
            tutorialUpdated.get().setTag(tutorialToUpdate.getTag());
            tutorialUpdated.get().setContent(tutorialToUpdate.getContent());
            tutorialUpdated.get().setShortDescription(tutorialToUpdate.getShortDescription());
            return ResponseEntity.ok(tutorialRepository.save(tutorialUpdated.get()));
        } else {
            throw new TutorialNotFoundException("This Tutorial with name:{%s} doesn't exist in DB".formatted(tutorialToUpdate.getName()));
        }
    }

}
