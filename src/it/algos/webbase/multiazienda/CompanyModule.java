package it.algos.webbase.multiazienda;

import it.algos.webbase.web.module.ModulePop;
import it.algos.webbase.web.search.SearchManager;
import it.algos.webbase.web.table.ATable;

/**
 * Module automatically filtered on the current Company
 * Created by alex on 8-03-2016.
 */
public class CompanyModule extends ModulePop {

    @SuppressWarnings("rawtypes")
    public CompanyModule(Class entity) {
        super(entity);
    }

    /**
     * Crea una Table già filtrata sulla company corrente
     * <p>
     * @return the Table
     */
    public ATable createTable() {
        return (new ETable(this));
    }// end of method

    /**
     * Create the Search Manager.
     * Il SearchManager crea i campi pop di ricerca già filtrati
     *
     * @return the SearchManager
     */
    public SearchManager createSearchManager() {
        SearchManager manager = new ESearchManager(this);
        return manager;
    }// end of method

}
