package it.algos.webbase.web.Command;

import com.vaadin.ui.Component;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.UI;

import com.vaadin.ui.Component;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.UI;

import java.util.List;

/**
 * Custom menu command to navigate to a specific state
 */
public class MenuCommand implements MenuBar.Command {

    private MenuBar mb;
    private String address;
    private Component comp;
    private UI ui;
    private Class clazz;
    private boolean viewCached;

    public MenuCommand(MenuBar mb, String address, Class clazz, boolean viewCached) {
        this.ui = UI.getCurrent();
        this.mb = mb;
        this.address = address;
        this.clazz = clazz;
        this.viewCached=viewCached;
    }

    public MenuCommand(MenuBar mb, String address, Class clazz) {
        this(mb, address, clazz, true);
    }// end of constructor


    public MenuCommand(MenuBar mb, String address, Component comp) {
        this(UI.getCurrent(), mb, address, comp);
    }// end of constructor

    public MenuCommand(UI ui, MenuBar mb, String address, Component comp) {
        super();
        this.ui = ui;
        this.mb = mb;
        this.address = address;
        this.comp = comp;
    }// end of constructor


    public void menuSelected(MenuBar.MenuItem selectedItem) {
        // Navigate to a specific state
        ui.getNavigator().navigateTo(address);

        // de-selects all the items in the menubar
        if (mb != null) {
            List<MenuBar.MenuItem> items = mb.getItems();
            for (MenuBar.MenuItem item : items) {
                deselectItem(item);
            } // fine del ciclo for
        }// fine del blocco if

        // highlights the selected item
        // the style name will be prepended automatically with "v-menubar-menuitem-"
        selectedItem.setStyleName("highlight");

        // it this item is inside another item, highlight also the parents in the chain
        MenuBar.MenuItem item = selectedItem;
        while (item.getParent() != null) {
            item = item.getParent();
            item.setStyleName("highlight");
        } // fine del ciclo while

    }// end of method

    /**
     * Recursively de-selects one item and all its children
     */
    private void deselectItem(MenuBar.MenuItem item) {
        item.setStyleName(null);
        List<MenuBar.MenuItem> items = item.getChildren();
        if (items != null) {
            for (MenuBar.MenuItem child : items) {
                deselectItem(child);
            } // fine del ciclo for
        }// fine del blocco if
    }// end of method

    /**
     * @return the address
     */
    public String getAddress() {
        return address;
    }// end of method

    /**
     * @return the comp
     */
    public Component getComponent() {
        return comp;
    }// end of method

    public Class getClazz() {
        return clazz;
    }

    public boolean isViewCached() {
        return viewCached;
    }

}// end of class
