package it.algos.webbase.web.toolbar;

import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Label;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.VerticalLayout;
import it.algos.webbase.web.table.ATable;

import java.util.ArrayList;
import java.util.HashMap;

@SuppressWarnings("serial")
public class TableToolbar extends Toolbar implements ATable.SelectionChangeListener {

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

        // initial sync call (no rows selected)
        syncButtons(false, false);

    }

    /**
     * Bottone new.
     * Usa il Font Awesome; deprecato l'uso dell'icona del Theme corrente
     */
    protected void addCreate() {
        MenuBar.MenuItem item;
        item = addButton("Nuovo", FontAwesome.PLUS, new MenuBar.Command() {
            public void menuSelected(MenuItem selectedItem) {
                fire(Bottoni.create);
            }
        });
        bottoni.put(Bottoni.create, item);
    }

    /**
     * Bottone edit.
     * Usa il Font Awesome; deprecato l'uso dell'icona del Theme corrente
     */
    protected void addEdit() {
        MenuBar.MenuItem item;
        item = addButton("Modifica", FontAwesome.PENCIL, new MenuBar.Command() {
            public void menuSelected(MenuItem selectedItem) {
                fire(Bottoni.edit);
            }
        });
        bottoni.put(Bottoni.edit, item);
    }

    /**
     * Bottone delete.
     * Usa il Font Awesome; deprecato l'uso dell'icona del Theme corrente
     */
    protected void addDelete() {
        MenuBar.MenuItem item;
        item = addButton("Elimina", FontAwesome.TRASH_O, new MenuBar.Command() {
            public void menuSelected(MenuItem selectedItem) {
                fire(Bottoni.delete);
            }
        });
        bottoni.put(Bottoni.delete, item);
    }

    /**
     * Bottone search.
     * Usa il Font Awesome; deprecato l'uso dell'icona del Theme corrente
     */
    protected void addSearch() {
        MenuBar.MenuItem item;
        item = addButton("Ricerca", FontAwesome.SEARCH, new MenuBar.Command() {
            public void menuSelected(MenuItem selectedItem) {
                fire(Bottoni.search);
            }
        });
        bottoni.put(Bottoni.search, item);
    }

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
            }
        });
        bottoni.put(Bottoni.selectedonly, item);


        item = itemSeleziona.addItem("Rimuovi selezionati", FontAwesome.FILE_TEXT_O, new MenuBar.Command() {
            public void menuSelected(MenuItem selectedItem) {
                fire(Bottoni.removeselected);
            }
        });
        bottoni.put(Bottoni.removeselected, item);


        item = itemSeleziona.addItem("Mostra tutti", FontAwesome.FILE, new MenuBar.Command() {
            public void menuSelected(MenuItem selectedItem) {
                fire(Bottoni.showall);
            }
        });
        bottoni.put(Bottoni.showall, item);


        item = itemSeleziona.addItem("Deseleziona tutti", FontAwesome.FILE_O, new MenuBar.Command() {
            public void menuSelected(MenuItem selectedItem) {
                fire(Bottoni.deselectall);
            }
        });
        bottoni.put(Bottoni.deselectall, item);

    }

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
            }
        }

    }

    public void fireEdit() {
        fire(Bottoni.edit);
    }

    public void setInfoText(String text) {
        infoPanel.setInfoText(text);
    }


    /**
     * Cambiata la selezione delle righe.
     * Possibilità di modificare l'aspetto (e la funzionalità) dei bottoni, eventualmente disabilitandoli
     * <p/>
     * Nuovo: sempre acceso
     * Modifica: acceso se è selezionata una ed una sola riga
     * Elimina: acceso se è selezionata una riga o più di una riga
     * Ricerca: sempre acceso
     * <p/>
     * Solo selezionati: acceso se è selezionata una riga o più di una riga
     * Rimuovi selezionati: acceso se è selezionata una riga o più di una riga
     * Mostra tutti: sempre acceso
     * Deseleziona tutti: acceso se è selezionata una riga o più di una riga
     */
    @Override
    public void selectionChanged(ATable.SelectionChangeEvent e) {
        syncButtons(e.isSingleRowSelected(), e.isMultipleRowsSelected());
    }

    /**
     * Sync the buttons state
     * <p/>
     *
     * @param singleSelected if a single row is selected in the table
     * @param multiSelected  if multiple rows (1+) are selected in the table
     */
    private void syncButtons(boolean singleSelected, boolean multiSelected) {
        
        if (bottoni.get(Bottoni.edit) != null) {
            bottoni.get(Bottoni.edit).setEnabled(singleSelected);
        }

        if (bottoni.get(Bottoni.delete) != null) {
            bottoni.get(Bottoni.delete).setEnabled(multiSelected);
        }

        if (bottoni.get(Bottoni.selectedonly) != null) {
            bottoni.get(Bottoni.selectedonly).setEnabled(multiSelected);
        }

        if (bottoni.get(Bottoni.removeselected) != null) {
            bottoni.get(Bottoni.removeselected).setEnabled(multiSelected);
        }

        if (bottoni.get(Bottoni.deselectall) != null) {
            bottoni.get(Bottoni.deselectall).setEnabled(multiSelected);
        }
    }

    public void setCreate(boolean enabled) {
        bottoni.get(Bottoni.create).setEnabled(enabled);
    }

    public void setEdit(boolean enabled) {
        bottoni.get(Bottoni.edit).setEnabled(enabled);
    }

    public void setDelete(boolean enabled) {
        bottoni.get(Bottoni.delete).setEnabled(enabled);
    }

    public void setSearch(boolean enabled) {
        bottoni.get(Bottoni.search).setEnabled(enabled);
    }

    public void setSelect(boolean enabled) {
        itemSeleziona.setEnabled(enabled);
    }


    public enum Bottoni {
        create, edit, delete, search, selectedonly, removeselected, showall, deselectall;
    }

    public interface TableToolbarListener {
        void create_();

        void edit_();

        void delete_();

        void search_();

        void selectedonly_();

        void removeselected_();

        void showall_();

        void deselectall_();

    }

    private class InfoPanel extends VerticalLayout {

        private Label infoLabel = new Label();

        public InfoPanel() {
            super();
            // addStyleName("yellowBg");
            addComponent(infoLabel);
        }

        public void setInfoText(String text) {
            infoLabel.setValue(text);
        }
    }// end of inner class

}// end of class
