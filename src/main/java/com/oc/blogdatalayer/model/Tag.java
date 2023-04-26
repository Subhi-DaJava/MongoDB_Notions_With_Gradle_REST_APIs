package com.oc.blogdatalayer.model;

public class Tag {
    private String name;
    private String slug;
    private String description;

    public Tag() {
    }

    public Tag(String name, String slug, String description) {
        this.name = name;
        this.slug = slug;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Tag{" +
                "name='" + name + '\'' +
                ", slug='" + slug + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
