package com.oc.blogdatalayer.repository;

import com.oc.blogdatalayer.model.Post;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * The interface is annotated with @Repository.
 * This annotation indicates that the class is responsible for communicating with a data source,
 * in this case, the database.
 */
@Repository
public interface PostRepository extends MongoRepository<Post, String> {
}
