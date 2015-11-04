package it.algos.webbase.web.toolbar;

import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Label;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.VerticalLayout;
import it.algos.webbase.web.lib.LibEvent;
import it.algos.webbase.web.table.ListSelectionListener;

import javax.swing.event.ListSelectionEvent;
import java.util.ArrayList;
import java.util.HashMap;

@SuppressWarnings("serial")
public class TableToolbar extends Toolbar implements ListSelectionListener {

    protected HashMap<Bottoni, MenuItem> bottoni = new HashMap<>();
    private ArrayList<TableToolbarListener> listeners = new ArrayList<TableToolbarListener>();
    private InfoPanel infoPanel = new InfoPanel();
    private MenuBar.MenuItem itemSeleziona;

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
     * Usa il Font Awesome; deprecato l'uso dell'icona del Theme corrente
     */
    protected void addCreate() {
        MenuBar.MenuItem item;
        item = addButton("Nuovo", FontAwesome.PLUS, new MenuBar.Command() {
            public void menuSelected(MenuItem selectedItem) {
                fire(Bottoni.create);
            }// end of inner method
        });// end of anonymous inner class
        bottoni.put(Bottoni.create, item);
    }// end of method

    /**
     * Bottone edit.
     * Usa il Font Awesome; deprecato l'uso dell'icona del Theme corrente
     */
    protected void addEdit() {
        MenuBar.MenuItem item;
        item = addButton("Modifica", FontAwesome.PENCIL, new MenuBar.Command() {
            public void menuSelected(MenuItem selectedItem) {
                fire(Bottoni.edit);
            }// end of inner method
        });// end of anonymous inner class
        bottoni.put(Bottoni.edit, item);
    }// end of method

    /**
     * Bottone delete.
     * Usa il Font Awesome; deprecato l'uso dell'icona del Theme corrente
     */
    protected void addDelete() {
        MenuBar.MenuItem item;
        item = addButton("Elimina", FontAwesome.TRASH_O, new MenuBar.Command() {
            public void menuSelected(MenuItem selectedItem) {
                fire(Bottoni.delete);
            }// end of inner method
        });// end of anonymous inner class
        bottoni.put(Bottoni.delete, item);
    }// end of method

    /**
     * Bottone search.
     * Usa il Font Awesome; deprecato l'uso dell'icona del Theme corrente
     */
    protected void addSearch() {
        MenuBar.MenuItem item;
        item = addButton("Ricerca", FontAwesome.SEARCH, new MenuBar.Command() {
            public void menuSelected(MenuItem selectedItem) {
                fire(Bottoni.search);
            }// end of inner method
        });// end of anonymous inner class
        bottoni.put(Bottoni.search, item);
    }// end of method

    /**
     * Bottone selection.
     * Usa il Font Awesome; deprecato l'uso dell'icona del Theme corrente
     */
    protected void addSelect() {
//        MenuBar.MenuItem itemSeleziona = null;
        MenuBar.MenuItem item;

        itemSeleziona = addButton("Seleziona", FontAwesome.LIST_UL, null);


        item = itemSeleziona.addItem("Solo selezionati", FontAwesome.FILE_TEXT, new MenuBar.Command() {
            public void menuSelected(MenuItem selectedItem) {
                fire(Bottoni.selectedonly);
            }// end of inner method
        });// end of anonymous inner class
        bottoni.put(Bottoni.selectedonly, item);


        item = itemSeleziona.addItem("Rimuovi selezionati", FontAwesome.FILE_TEXT_O, new MenuBar.Command() {
            public void menuSelected(MenuItem selectedItem) {
                fire(Bottoni.removeselected);
            }// end of inner method
        });// end of anonymous inner class
        bottoni.put(Bottoni.removeselected, item);


        item = itemSeleziona.addItem("Mostra tutti", FontAwesome.FILE, new MenuBar.Command() {
            public void menuSelected(MenuItem selectedItem) {
                fire(Bottoni.showall);
            }// end of inner method
        });// end of anonymous inner class
        bottoni.put(Bottoni.showall, item);


        item = itemSeleziona.addItem("Deseleziona tutti", FontAwesome.FILE_O, new MenuBar.Command() {
            public void menuSelected(MenuItem selectedItem) {
                fire(Bottoni.deselectall);
            }// end of inner method
        });// end of anonymous inner class
        bottoni.put(Bottoni.deselectall, item);

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

    /**
     * Cambiata la selezione delle righe.
     * Possibilità di modificare l'aspetto (e la funzionalità) dei bottoni, eventualmente disabilitandoli
     * <p>
     * Nuovo: sempre acceso
     * Modifica: acceso se è selezionata una ed una sola riga
     * Elimina: acceso se è selezionata una riga o più di una riga
     * Ricerca: sempre acceso
     * <p>
     * Solo selezionati: acceso se è selezionata una riga o più di una riga
     * Rimuovi selezionati: acceso se è selezionata una riga o più di una riga
     * Mostra tutti: sempre acceso
     * Deseleziona tutti: acceso se è selezionata una riga o più di una riga
     */
    @Override
    public void valueChanged(ListSelectionEvent event) {
        boolean unasola = LibEvent.isUnaSolaRigaSelezionata(event);
        boolean unaopiu = LibEvent.isUnaOPiuRigheSelezionate(event);
        boolean molte = LibEvent.isDiverseRigheSelezionate(event);
        boolean nessuna = LibEvent.isNessunaRigaSelezionata(event);

        if (bottoni.get(Bottoni.edit) != null) {
            bottoni.get(Bottoni.edit).setEnabled(unasola);
        }// end of if cycle

        if (bottoni.get(Bottoni.delete) != null) {
            bottoni.get(Bottoni.delete).setEnabled(unaopiu);
        }// end of if cycle

        if (bottoni.get(Bottoni.selectedonly) != null) {
            bottoni.get(Bottoni.selectedonly).setEnabled(unaopiu);
        }// end of if cycle

        if (bottoni.get(Bottoni.removeselected) != null) {
            bottoni.get(Bottoni.removeselected).setEnabled(unaopiu);
        }// end of if cycle

        if (bottoni.get(Bottoni.deselectall) != null) {
            bottoni.get(Bottoni.deselectall).setEnabled(unaopiu);
        }// end of if cycle

    }// end of method


    public void setCreate(boolean enabled) {
        bottoni.get(Bottoni.create).setEnabled(enabled);
    }//end of setter method

    public void setEdit(boolean enabled) {
        bottoni.get(Bottoni.edit).setEnabled(enabled);
    }//end of setter method

    public void setDelete(boolean enabled) {
        bottoni.get(Bottoni.delete).setEnabled(enabled);
    }//end of setter method

    public void setSearch(boolean enabled) {
        bottoni.get(Bottoni.search).setEnabled(enabled);
    }//end of setter method

    public void setSelect(boolean enabled) {
        itemSeleziona.setEnabled(enabled);
    }//end of setter method


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
