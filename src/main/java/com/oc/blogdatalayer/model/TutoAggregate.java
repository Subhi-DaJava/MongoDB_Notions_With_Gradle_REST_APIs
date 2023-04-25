package com.oc.blogdatalayer.model;

import org.springframework.data.annotation.Id;

import java.util.List;

public class TutoAggregate {
    private @Id String category;
    private List<TutorialExtract> tutorials;

    public String getCategory() {
        return category;
    }

    public List<TutorialExtract> getTutorials() {
        return tutorials;
    }
}
