package it.algos.webbase.web.table;

/**
 * Interfaccia della classe che deve usare ListSelectionListener
 * Serve per forzare i metodi indispensabili
 * <p>
 * La classe deve prevedere i metodi addListListener(), removeAllListListeners() e setListListener()
 * Nella classe si registrano i listeners (istanze di classi che sono interessate all'evento)
 * I listeners verrano informati dell'evento
 */
public interface ListSelection {

    /**
     * Adds a listener
     */
    void addListListener(ListSelectionListener list);

    /**
     * Removes all the listeners
     */
    void removeAllListListeners();

    /**
     * Registers a unique listener.
     * All the previous listeners are deleted
     */
    void setListListener(ListSelectionListener list);

}// end of interface

