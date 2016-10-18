package it.algos.webbase.web.lib;

import com.vaadin.ui.DefaultFieldFactory;
import com.vaadin.ui.Field;
import it.algos.webbase.web.field.*;

import javax.persistence.metamodel.Attribute;
import javax.persistence.metamodel.PluralAttribute;
import javax.persistence.metamodel.SingularAttribute;
import java.lang.annotation.Annotation;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by gac on 18 ott 2016.
 * Creazione dei field dalla annotation
 */
public class LibField {

    /**
     * Create a single field.
     * The field type is chosen according to the annotation.
     *
     * @param attr the metamodel Attribute
     */
    @SuppressWarnings("all")
    public static Field createField(Attribute attr) {
        Field vaadinField = null;
        java.lang.reflect.Field javaField = null;
        Annotation annotation = null;
        String fieldType = "";
        AIField fieldAnnotation = null;

        try { // prova ad eseguire il codice
            javaField = attr.getJavaMember().getDeclaringClass().getDeclaredField(attr.getName());
        } catch (Exception unErrore) { // intercetta l'errore
            return createFieldJavaType(attr);
        }// fine del blocco try-catch

        if (javaField != null) {
            annotation = javaField.getAnnotation(AIField.class);
        } else {
            return createFieldJavaType(attr);
        }// end of if/else cycle

        if (annotation != null && annotation instanceof AIField) {
            fieldAnnotation = (AIField) annotation;
        } else {
            return createFieldJavaType(attr);
        }// end of if/else cycle

        if (fieldAnnotation != null) {
            switch (fieldAnnotation.type()) {
                case text:
                    vaadinField = new TextField();
                    ((TextField) vaadinField).setInputPrompt(fieldAnnotation.prompt());
                    ((TextField) vaadinField).setDescription(fieldAnnotation.help());
                    ((TextField) vaadinField).setEnabled(fieldAnnotation.enabled());
                    break;
                case email:
                    vaadinField = new EmailField();
                    ((EmailField) vaadinField).setInputPrompt(fieldAnnotation.prompt());
                    ((EmailField) vaadinField).setDescription(fieldAnnotation.help());
                    break;
                case checkbox:
                    vaadinField = new CheckBoxField();
                    ((CheckBoxField) vaadinField).setDescription(fieldAnnotation.help());
                    break;
                case area:
                    vaadinField = new TextArea();
                    ((TextArea) vaadinField).setInputPrompt(fieldAnnotation.prompt());
                    ((TextArea) vaadinField).setDescription(fieldAnnotation.help());
                    break;
                case date:
                    vaadinField = new DateField();
                    ((DateField) vaadinField).setDescription(fieldAnnotation.help());
                    break;
                case password:
                    vaadinField = new PasswordField();
                    ((PasswordField) vaadinField).setDescription(fieldAnnotation.help());
                    ((PasswordField) vaadinField).setEnabled(fieldAnnotation.enabled());
                    break;
                default: // caso non definito
                    vaadinField = new TextField();
                    ((TextField) vaadinField).setInputPrompt(fieldAnnotation.prompt());
                    ((TextField) vaadinField).setDescription(fieldAnnotation.help());
                    ((TextField) vaadinField).setEnabled(fieldAnnotation.enabled());
                    break;
            } // fine del blocco switch
            vaadinField.setRequired(fieldAnnotation.required());
            vaadinField.setCaption(fieldAnnotation.caption());
            vaadinField.setWidth(fieldAnnotation.width());

            return vaadinField;
        } else {
            return createFieldJavaType(attr);
        }// end of if/else cycle
    }// end of static method

    /**
     * Create a single field.
     * The field type is chosen according to the Java type.
     *
     * @param attr the metamodel Attribute
     */
    public static Field createFieldJavaType(Attribute attr) {
        Field field = null;

        if (attr != null) {


            try {

                if (attr.isAssociation()) { // questo può fallire!!

                    // relazione OneToMany - usiamo un campo lista (?)
                    if (attr instanceof PluralAttribute) {
                        PluralAttribute pa = (PluralAttribute) attr;
                        Class clazz = pa.getElementType().getJavaType();
                        //field = new RelatedComboField(clazz);
                        //field=null;// todo qui ci vuole una lista
                    }

                    // relazione ManyToOne - usiamo un campo combo
                    if (attr instanceof SingularAttribute) {
                        SingularAttribute sa = (SingularAttribute) attr;
                        Class clazz = sa.getJavaType();
                        field = new RelatedComboField(clazz);
                    }

                } else {

                    Class clazz = attr.getJavaType();

                    if (clazz.equals(Boolean.class) || clazz.equals(boolean.class)) {
                        field = new CheckBoxField();
                    }

                    if (clazz.equals(String.class)) {
                        field = new TextField();
                    }

                    if (clazz.equals(Integer.class) || clazz.equals(int.class)) {
                        field = new IntegerField();
                    }

                    if (clazz.equals(Long.class) || clazz.equals(long.class)) {
                        field = new LongField();
                    }

                    if (clazz.equals(Date.class)) {
                        field = new DateField();
                    }

                    if (clazz.equals(BigDecimal.class)) {
                        field = new DecimalField();
                    }

                    if (clazz.equals(Timestamp.class)) {
                        field = new DateField();
                    }

                    if (clazz.isEnum()) {
                        Object[] values = clazz.getEnumConstants();
                        ArrayComboField acf = new ArrayComboField(values);
                        acf.setNullSelectionAllowed(true);
                        field = acf;
                    }

                }

                // create and assign the caption
                if (field != null) {
                    String caption = DefaultFieldFactory.createCaptionByPropertyId(attr.getName());
                    field.setCaption(caption);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }


        }


        return field;

    }// end of static method

}// end of abstract static class
