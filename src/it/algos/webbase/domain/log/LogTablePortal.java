package it.algos.webbase.domain.log;

import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.data.Container;
import com.vaadin.data.util.filter.Compare;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.MenuBar;
import it.algos.webbase.web.module.ModulePop;
import it.algos.webbase.web.table.TablePortal;
import it.algos.webbase.web.toolbar.TableToolbar;

/**
 * Created by gac on 23 set 2015.
 *
 * Sovrascrive la classe standard per aggiungere un bottone/menu di filtro sul parametro Livello
 */
public class LogTablePortal extends TablePortal {
    private TableToolbar toolbar;

    public LogTablePortal(ModulePop modulo) {
        super(modulo);
    }// end of constructor

    public TableToolbar createToolbar() {
        toolbar = super.createToolbar();

        addSelect();

        return toolbar;
    }// end of method


    /**
     * Livello selection.
     */
    private void addSelect() {
        MenuBar.MenuItem item = null;

        item = toolbar.addButton("Livello", FontAwesome.NAVICON, null);

        item.addItem(Livello.debug.toString(), FontAwesome.FILE_TEXT, new MenuBar.Command() {
            public void menuSelected(MenuBar.MenuItem selectedItem) {
                filtroDebug();
            }// end of inner method
        });// end of anonymous inner class

        item.addItem(Livello.info.toString(), FontAwesome.FILE_TEXT, new MenuBar.Command() {
            public void menuSelected(MenuBar.MenuItem selectedItem) {
                filtroInfo();
            }// end of inner method
        });// end of anonymous inner class

        item.addItem(Livello.warn.toString(), FontAwesome.FILE_TEXT, new MenuBar.Command() {
            public void menuSelected(MenuBar.MenuItem selectedItem) {
                filtroWarn();
            }// end of inner method
        });// end of anonymous inner class

        item.addItem(Livello.error.toString(), FontAwesome.FILE_TEXT, new MenuBar.Command() {
            public void menuSelected(MenuBar.MenuItem selectedItem) {
                filtroError();
            }// end of inner method
        });// end of anonymous inner class
    }// end of method


    /**
     * Shows in the table only the needed level
     */
    private void setFiltro(Container.Filter filter) {
        JPAContainer<?> cont = getTable().getJPAContainer();

        if (cont != null && filter != null) {
            cont.removeAllContainerFilters();
            cont.addContainerFilter(filter);
            cont.refresh();
        }// end of if cycle
    }// end of method

    /**
     * Shows in the table only the debug level
     * Creates a filter corresponding to the debug level rows in the table
     */
    private void filtroDebug() {
        setFiltro(new Compare.GreaterOrEqual(Log_.livello.getName(), Livello.debug));
    }// end of method

    /**
     * Shows in the table only the info level
     * Creates a filter corresponding to the info level rows in the table
     */
    private void filtroInfo() {
        setFiltro(new Compare.GreaterOrEqual(Log_.livello.getName(), Livello.info));
    }// end of method

    /**
     * Shows in the table only the warn level
     * Creates a filter corresponding to the warn level rows in the table
     */
    private void filtroWarn() {
        setFiltro(new Compare.GreaterOrEqual(Log_.livello.getName(), Livello.warn));
    }// end of method

    /**
     * Shows in the table only the error level
     * Creates a filter corresponding to the error level rows in the table
     */
    private void filtroError() {
        setFiltro(new Compare.GreaterOrEqual(Log_.livello.getName(), Livello.error));
    }// end of method

}// end of class

