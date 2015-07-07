package it.algos.web.toolbar;

import java.util.ArrayList;

import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Label;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.VerticalLayout;

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

    protected void addCreate() {
        addButton("Nuovo", new ThemeResource("img/action_add.png"), new MenuBar.Command() {
            public void menuSelected(MenuItem selectedItem) {
                fire(Bottoni.create);
            }// end of inner method
        });// end of anonymous inner class

//		addButton("Nuovo", LibResource.getImgResource("action_add.png"), new MenuBar.Command() {
//			public void menuSelected(MenuItem selectedItem) {
//				fire(Bottoni.create);
//			}// end of inner method
//		});// end of anonymous inner class

    }// end of method

    protected void addEdit() {
        addButton("Modifica", new ThemeResource("img/action_edit.png"), new MenuBar.Command() {
            public void menuSelected(MenuItem selectedItem) {
                fire(Bottoni.edit);
            }// end of inner method
        });// end of anonymous inner class
    }// end of method

    protected void addDelete() {
        addButton("Elimina", new ThemeResource("img/action_delete.png"), new MenuBar.Command() {
            public void menuSelected(MenuItem selectedItem) {
                fire(Bottoni.delete);
            }// end of inner method
        });// end of anonymous inner class
    }// end of method

    protected void addSearch() {
        addButton("Ricerca", new ThemeResource("img/action_find.png"), new MenuBar.Command() {
            public void menuSelected(MenuItem selectedItem) {
                fire(Bottoni.search);
            }// end of inner method
        });// end of anonymous inner class
    }// end of method

    protected void addSelect() {

        MenuBar.MenuItem item = addButton("Selez.", new ThemeResource("img/action_select.png"), null);

        item.addItem("Solo selezionati", null, new MenuBar.Command() {
            public void menuSelected(MenuItem selectedItem) {
                fire(Bottoni.selectedonly);
            }// end of inner method
        });// end of anonymous inner class

        item.addItem("Rimuovi selezionati", null, new MenuBar.Command() {
            public void menuSelected(MenuItem selectedItem) {
                fire(Bottoni.removeselected);
            }// end of inner method
        });// end of anonymous inner class

        item.addItem("Mostra tutti", null, new MenuBar.Command() {
            public void menuSelected(MenuItem selectedItem) {
                fire(Bottoni.showall);
            }// end of inner method
        });// end of anonymous inner class

        item.addItem("Deseleziona tutti", null, new MenuBar.Command() {
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

    public enum Bottoni {
        create, edit, delete, search, selectedonly, removeselected, showall, deselectall;
    }// end of enumeration

}// end of class
