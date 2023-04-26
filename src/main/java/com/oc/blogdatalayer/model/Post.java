package com.oc.blogdatalayer.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * This annotation provided by Spring Data MongoDB is used to indicate that the class represents a document.
 * Specify the name of the collection using the annotation attribute named "collection".
 * Each attribute of the class represents one of the characteristics of the document.
 * Note: @Field(“nom”) and @Transient
 */
@Document(collection = "posts")
public class Post {
    @Id
    private String id;
    @Indexed(unique = true)
    private String name;
    private String content;
    private LocalDateTime date;
    private Tag tag;
    private List<Comment> comments = new ArrayList<>();

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public Tag getTag() {
        return tag;
    }

    public void setTag(Tag tag) {
        this.tag = tag;
    }

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

    @Override
    public String toString() {
        return "Post{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", content='" + content + '\'' +
                ", date=" + date +
                ", tag=" + tag +
                '}';
    }
}
