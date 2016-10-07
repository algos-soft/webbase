package it.algos.webbase.web.field;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by gac on 05 ott 2016.
 * AlgosInterfaceField (AIField)
 * Annotation to add some property for a single field.
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD) //can use in field only.
public @interface AIField {

    AFType type() default AFType.text;

    String width() default "12em";

    String caption() default "";

    String prompt() default "";

    String help() default "";

    boolean enabled() default true;

    boolean required() default false;

    String error() default "";

}// end of interface annotation
