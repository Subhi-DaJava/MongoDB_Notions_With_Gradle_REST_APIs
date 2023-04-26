package com.oc.blogdatalayer.repository;

import com.oc.blogdatalayer.model.LightPost;
import com.oc.blogdatalayer.model.Post;
import com.oc.blogdatalayer.model.PostAggregate;
import org.springframework.data.mongodb.repository.Aggregation;
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
     *
     * @return LightPost
     */
    List<LightPost> findByOrderByDateDesc();

    /**
     * value="{}": specifies the MongoDB query filter, here empty. This means that all documents in the collection will be retrieved.
     * fields="{_id: 1, name: 1}": specifies the fields to include in the results.
     * Here, only the _id and name fields will be returned, with a value of 1 for each field to indicate that they should be included.
     * sort="{ date: -1 }": specifies the sort order of the results. Here, the results will be sorted in descending order of the value of the date field.
     *
     * @return Post list only id and name
     */
    @Query(value = "{}", fields = "{_id: 1, name: 1}", sort = "{date: -1}")
    List<Post> findByIdAndNameExcludeOthers();

    /**
     * This method is a Spring Data MongoDB repository method that uses the Aggregation Framework of MongoDB to retrieve all the names of the documents in a collection.
     * The @Aggregation annotation is used to define the aggregation pipeline as a JSON string.
     * In this case, the pipeline consists of a single $project stage, which selects the name field of each document and renames it to _id.
     * This means that the result of the aggregation will be a list of strings, where each string represents the _id (which is the name) of a document.
     * <></>
     * This annotation (@Aggregation) allows us to define a sequence of operations (known as a pipeline) for the Aggregation Framework of MongoDB,
     * similar to what we would do in a native query.
     * The pipeline can consist of one or more stages, each performing a specific operation on the input data.
     * Examples of stages include filtering ($match), grouping ($group), projecting ($project), and sorting ($sort).
     * By using the @Aggregation annotation in a Spring Data MongoDB repository method,
     * we can define the pipeline stages as a JSON string or a sequence of operation objects,
     * and then execute the pipeline against the MongoDB server to retrieve the desired result.
     *
     * @return List of Names
     */
    @Aggregation("{ '$project': { '_id' : '$name' } }")
    List<String> findAllNames();

    /**
     * This method uses the @Aggregation annotation to define a MongoDB aggregation pipeline that groups posts by date and returns a list of PostAggregate objects,
     * each representing a group of posts with the same date and the names of the posts in that group.
     * The pipeline consists of a single stage ($group) that takes the input data and groups it by the date field,
     * using the $date operator as the grouping criterion. For each group, the pipeline creates a new document with two fields: _id,
     * which contains the date value, and names, which is an array of the distinct name values of all the posts in that group.
     * The $addToSet operator is used to add each distinct name value to the names array.
     * The output of this pipeline is a list of PostAggregate objects, each containing the date value and the list of names for a group of posts with the same date.
     * @return List of PostAggregate
     */
    @Aggregation(" { '$group': {'_id' : '$date', 'names' : { '$addToSet': '$name'} } }")
    List<PostAggregate> groupPostsByDate();

    void deleteByName(String name);
}
