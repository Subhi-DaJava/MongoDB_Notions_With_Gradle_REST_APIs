package com.oc.blogdatalayer.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

/**
 * This annotation provided by Spring Data MongoDB is used to indicate that the class represents a document.
 * Specify the name of the collection using the annotation attribute named "collection".
 * Each attribute of the class represents one of the characteristics of the document.
 * Note: @Field(“nom”) and @Transient
 */
@Document(collation = "posts")
public class Post {
    @Id
    private String id;
    private String name;
    private String content;
    private LocalDateTime date;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
}
