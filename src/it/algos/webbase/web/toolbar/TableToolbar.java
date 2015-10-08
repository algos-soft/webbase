package it.algos.webbase.web.toolbar;

import com.vaadin.server.FontAwesome;
import com.vaadin.server.Resource;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.VerticalLayout;
import it.algos.webbase.web.AlgosApp;

import java.util.ArrayList;

@SuppressWarnings("serial")
public class TableToolbar extends Toolbar {

    private ArrayList<TableToolbarListener> listeners = new ArrayList<TableToolbarListener>();
    private InfoPanel infoPanel = new InfoPanel();

    public TableToolbar() {
        super();

        addCreate();
        addEdit();
        addDelete();
        addSearch();
        addSelect();

        addHelperComponent(infoPanel);

    }// end of constructor

    /**
     * Bottone new.
     * A seconda del flag, usa il Font Awesome oppure l'icona del Theme corrente
     */
    protected void addCreate() {
        addButton("Nuovo", FontAwesome.PLUS, new MenuBar.Command() {
            public void menuSelected(MenuItem selectedItem) {
                fire(Bottoni.create);
            }// end of inner method
        });// end of anonymous inner class
    }// end of method

    /**
     * Bottone edit.
     * A seconda del flag, usa il Font Awesome oppure l'icona del Theme corrente
     */
    protected void addEdit() {
        addButton("Modifica", FontAwesome.PENCIL, new MenuBar.Command() {
            public void menuSelected(MenuItem selectedItem) {
                fire(Bottoni.edit);
            }// end of inner method
        });// end of anonymous inner class
    }// end of method

    /**
     * Bottone delete.
     * A seconda del flag, usa il Font Awesome oppure l'icona del Theme corrente
     */
    protected void addDelete() {
        addButton("Elimina", FontAwesome.TRASH_O, new MenuBar.Command() {
            public void menuSelected(MenuItem selectedItem) {
                fire(Bottoni.delete);
            }// end of inner method
        });// end of anonymous inner class
    }// end of method

    /**
     * Bottone search.
     * A seconda del flag, usa il Font Awesome oppure l'icona del Theme corrente
     */
    protected void addSearch() {
        addButton("Ricerca", FontAwesome.SEARCH, new MenuBar.Command() {
            public void menuSelected(MenuItem selectedItem) {
                fire(Bottoni.search);
            }// end of inner method
        });// end of anonymous inner class
    }// end of method

    /**
     * Bottone selection.
     * A seconda del flag, usa il Font Awesome oppure l'icona del Theme corrente
     */
    protected void addSelect() {
        MenuBar.MenuItem item = null;

        item = addButton("Seleziona", FontAwesome.LIST_UL, null);


        item.addItem("Solo selezionati", FontAwesome.FILE_TEXT, new MenuBar.Command() {
            public void menuSelected(MenuItem selectedItem) {
                fire(Bottoni.selectedonly);
            }// end of inner method
        });// end of anonymous inner class


        item.addItem("Rimuovi selezionati", FontAwesome.FILE_TEXT_O, new MenuBar.Command() {
            public void menuSelected(MenuItem selectedItem) {
                fire(Bottoni.removeselected);
            }// end of inner method
        });// end of anonymous inner class


        item.addItem("Mostra tutti", FontAwesome.FILE, new MenuBar.Command() {
            public void menuSelected(MenuItem selectedItem) {
                fire(Bottoni.showall);
            }// end of inner method
        });// end of anonymous inner class


        item.addItem("Deseleziona tutti", FontAwesome.FILE_O, new MenuBar.Command() {
            public void menuSelected(MenuItem selectedItem) {
                fire(Bottoni.deselectall);
            }// end of inner method
        });// end of anonymous inner class

    }// end of method

    public void addToolbarListener(TableToolbarListener listener) {
        this.listeners.add(listener);
    }

    private void fire(Bottoni bottone) {
        for (TableToolbarListener l : listeners) {
            switch (bottone) {
                case create:
                    l.create_();
                    break;
                case edit:
                    l.edit_();
                    break;
                case delete:
                    l.delete_();
                    break;
                case search:
                    l.search_();
                    break;
                case selectedonly:
                    l.selectedonly_();
                    break;
                case removeselected:
                    l.removeselected_();
                    break;
                case showall:
                    l.showall_();
                    break;
                case deselectall:
                    l.deselectall_();
                    break;

                default:
                    break;
            }// end of switch cycle
        }// end of for cycle

    }// end of method

    public void fireEdit() {
        fire(Bottoni.edit);
    }// end of method

    public void setInfoText(String text) {
        infoPanel.setInfoText(text);
    }// end of method

    public enum Bottoni {
        create, edit, delete, search, selectedonly, removeselected, showall, deselectall;
    }// end of enumeration

    public interface TableToolbarListener {
        public void create_();

        public void edit_();

        public void delete_();

        public void search_();

        public void selectedonly_();

        public void removeselected_();

        public void showall_();

        public void deselectall_();

    }// end of interface

    private class InfoPanel extends VerticalLayout {

        private Label infoLabel = new Label();

        public InfoPanel() {
            super();
            // addStyleName("yellowBg");
            addComponent(infoLabel);
        }// end of inner method

        public void setInfoText(String text) {
            infoLabel.setValue(text);
        }// end of inner method
    }// end of inner class

}// end of class
