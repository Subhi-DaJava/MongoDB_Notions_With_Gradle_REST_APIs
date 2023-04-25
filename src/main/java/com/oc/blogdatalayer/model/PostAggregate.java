package com.oc.blogdatalayer.model;

import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;
import java.util.List;

/**
 * This @Aggregation method uses the $group operator to group the documents by their date field,
 * which is annotated with @Id. For each group, it creates a new document with two fields:
 * _id, which corresponds to the value used to group the documents (in this case, the date field).
 * names, which receives the list of names of the articles for the date defined in the previous attribute.
 * The $addToSet operator is used to add each name to the list without duplicates.
 */
public class PostAggregate {
    private @Id LocalDateTime date;

    public LocalDateTime getDate() {
        return date;
    }

    public List<String> getNames() {
        return names;
    }

    private List<String> names;

}
