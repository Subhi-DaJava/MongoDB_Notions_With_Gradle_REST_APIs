package com.oc.blogdatalayer.repository;

import com.oc.blogdatalayer.model.NameAndDescriptionTuto;
import com.oc.blogdatalayer.model.TutoAggregate;
import com.oc.blogdatalayer.model.Tutorial;
import org.springframework.data.mongodb.repository.Aggregation;
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

    @Aggregation(" { '$project': {'_id' : '$category'} } ")
    List<String> findAllCategories();

    //@Aggregation(" { $group: { _id : $category, tutorials : { $addToSet : {'name': '$name', 'shortDescription':'$shortDescription'} } } }")

    /**
     * This method uses MongoDB Aggregation Framework to group tutorials by their category and return a list of TutoAggregate objects.
     * The $group operator is used to group documents by the category field.
     * The _id field is set to the category field, which means that documents with the same category value will be grouped together.
     * The tutorials field is populated using the $addToSet operator,
     * which adds the specified fields to the array only if they do not already exist.
     * In this case, it adds an object containing the name and shortDescription fields of the tutorial.
     * The method returns a list of TutoAggregate objects, which have two fields:
     * _id: the category of the tutorials.
     * tutorials: a list of name and shortDescription pairs for each tutorial in the category.
     * @return List of TutoAggregate
     */
    @Aggregation(" { '$group': { '_id': '$category', 'tutorials' : {'$addToSet': {'name': '$name', 'shortDescription': '$shortDescription'} } } }")
    List<TutoAggregate> groupByCategoryAggregateNamesAndShortDescription();
}
