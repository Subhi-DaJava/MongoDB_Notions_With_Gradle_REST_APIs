package com.oc.blogdatalayer.repository;

import com.oc.blogdatalayer.model.Tutorial;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TutorialRepository extends MongoRepository<Tutorial, String> {
}
