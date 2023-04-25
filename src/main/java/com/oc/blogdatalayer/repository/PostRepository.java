package com.oc.blogdatalayer.repository;

import com.oc.blogdatalayer.model.LightPost;
import com.oc.blogdatalayer.model.Post;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * The interface is annotated with @Repository.
 * This annotation indicates that the class is responsible for communicating with a data source,
 * in this case, the database.
 */
@Repository
public interface PostRepository extends MongoRepository<Post, String> {
    Post findByName(String postName);
    List<Post> findByContentContaining(String text);

    List<Post> findByDateAfter(LocalDateTime date);

    /**
     * The findByOrderByDateDesc() method has the particularity, at the return type level, of specifying the interface of our projection, namely LightPost
     * @return LightPost
     */
    List<LightPost> findByOrderByDateDesc();

    /**
     * value="{}": specifies the MongoDB query filter, here empty. This means that all documents in the collection will be retrieved.
     * fields="{_id: 1, name: 1}": specifies the fields to include in the results.
     * Here, only the _id and name fields will be returned, with a value of 1 for each field to indicate that they should be included.
     * sort="{ date: -1 }": specifies the sort order of the results. Here, the results will be sorted in descending order of the value of the date field.
     * @return Post list only id and name
     */
    @Query(value = "{}", fields = "{_id: 1, name: 1}", sort= "{date: -1}")
    List<Post> findByIdAndNameExcludeOthers();
}
