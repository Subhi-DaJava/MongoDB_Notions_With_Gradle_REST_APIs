package com.oc.blogdatalayer.model;

/**
 * A projection corresponds to retrieving a subset of the document. Let's see together how to do it
 * This lightweight post, as understood by the name LightPost, is composed only of a getter for the id and another one for the name.
 * In the package of model
 *
 * Spring Data MongoDB just retrieves the id and name, not all the document information
 */
public interface LightPost {
    String getId();

    String getName();
}
