package it.algos.webbase.web.lib;

import com.vaadin.data.Property;
import com.vaadin.ui.Table;

import java.sql.Timestamp;

/**
 * Created by gac on 06 set 2015.
 * .
 */
public abstract class LibTable {

    /**
     * Recupera dalla tavola il valore di una cella di tipo Timestamp
     */
    public static long getTime(Table source, Object itemId, Object columnId) {
        long value = 0;
        Timestamp timestamp = getTimestamp(source, itemId, columnId);

        if (timestamp != null) {
            value =  timestamp.getTime();
        }// fine del blocco if

        return value;
    }// end of static method

    /**
     * Recupera dalla tavola il valore di una cella di tipo Timestamp
     */
    public static Timestamp getTimestamp(Table source, Object itemId, Object columnId) {
        Timestamp value = null;
        Property property = getProperty(source, itemId, columnId);
        Object objValue;

        if (property != null) {
            objValue = property.getValue();

            if (objValue != null && objValue instanceof Timestamp) {
                value = ((Timestamp) objValue);
            }// fine del blocco if
        }// fine del blocco if

        return value;
    }// end of static method

    /**
     * Recupera la property di una cella della tavola
     */
    private static Property getProperty(Table source, Object itemId, Object columnId) {
        return source.getContainerProperty(itemId, columnId);
    }// end of static method

}// end of abstract static class
