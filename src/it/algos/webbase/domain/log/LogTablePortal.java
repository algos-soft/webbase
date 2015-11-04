package it.algos.webbase.domain.log;

import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.data.Container;
import com.vaadin.data.util.filter.Compare;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.MenuBar;
import it.algos.webbase.web.module.ModulePop;
import it.algos.webbase.web.table.ATable;
import it.algos.webbase.web.table.TablePortal;
import it.algos.webbase.web.toolbar.TableToolbar;

/**
 * Created by gac on 23 set 2015.
 * <p>
 * Sovrascrive la classe standard per aggiungere un bottone/menu di filtro sul parametro Livello
 */
public class LogTablePortal extends TablePortal {
    private TableToolbar toolbar;

    public LogTablePortal(ModulePop modulo) {
        super(modulo);
    }// end of constructor

    public TableToolbar createToolbar() {
        toolbar = super.createToolbar();
        toolbar.setCreate(false);

        addSelectLivello();

        return toolbar;
    }// end of method


    /**
     * Livello selection.
     * <p>
     * Costruisce un menu per selezionare il livello di dettaglio dei records da filtrare
     * Costruisce i menuItem in funzione dei valori della enumeration Livello
     * L'ordine di presentazione è identico all'ordine in cui è stata costruita la enumeration (NON CAMBIARE ORDINE)
     */
    private void addSelectLivello() {
        MenuBar.MenuItem item = null;

        item = toolbar.addButton("Livello", FontAwesome.NAVICON, null);

        for (Livello livello : Livello.values()) {
            item.addItem(livello.toString(), FontAwesome.FILE_TEXT, new MenuBar.Command() {
                public void menuSelected(MenuBar.MenuItem selectedItem) {
                    setFiltro(livello);
                }// end of inner method
            });// end of anonymous inner class
        }// end of for cycle

    }// end of method


    /**
     * Shows in the table only the needed level
     * Creates a filter corresponding to the needed level in the table
     * I filtri sono comprensivi del livello sottostante (GreaterOrEqual)
     */
    private void setFiltro(Livello livello) {
        Container.Filter filter = null;
        ATable table = null;
        JPAContainer<?> cont = null;

        if (livello != null) {
            table = this.getTable();
        }// end of if cycle

        if (table != null) {
            cont = table.getJPAContainer();
        }// end of if cycle

        if (cont != null) {
            filter = new Compare.GreaterOrEqual(Log_.livello.getName(), livello);
        }// end of if cycle

        if (filter != null) {
            cont.removeAllContainerFilters();
            cont.addContainerFilter(filter);
            cont.refresh();
        }// end of if cycle
    }// end of method

}// end of class

