package it.algos.webbase.web.entity;

/**
 * Created by alex on 17-12-2015.
 * Annotation to declare default sort fields in BaseEntities
 */
public @interface DefaultSort {

    String[] names() default {};

}
