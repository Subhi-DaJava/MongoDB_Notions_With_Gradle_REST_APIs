package com.oc.blogdatalayer.repository;

import com.oc.blogdatalayer.model.NameAndDescriptionTuto;
import com.oc.blogdatalayer.model.Tutorial;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TutorialRepository extends MongoRepository<Tutorial, String> {
    Tutorial findByName(String tutorialName);

    List<Tutorial> findByShortDescriptionContaining(String givenWord);

    List<NameAndDescriptionTuto> findByOrderByNameAsc();

    @Query(value = "{}", fields = "{_id: 1, name: 1}", sort = "{category: -1}")
    List<Tutorial> findIdAndNameExcludeOthers();
}
