package it.algos.webbase.web.importexport;

/**
 * Provides the column titles and returns the export values for one source entity.
 * May return more than one title and value for one source column
 * because a single source column can be splitted to multiple export columns.
 * e.g. you can have a Person object as source, and the coulmns exported
 * might be "name", "age" and "address".
 * This class must be subclassed and the subclass should implement getExportValues()
 * Created by alex on 31-05-2015.
 */
public abstract class ExportProvider<EntityItem> {

    /**
     * @return the column titles
     * (the order must match getExportValues)
     */
    public abstract String[] getTitles();

    /**
     * Returns the export values for one source object.
     * @param item the source item
     * @return the values to be exported
     */
    public abstract Object[] getExportValues(EntityItem item);

}
