package it.algos.webbase.web.Command;

import com.vaadin.navigator.View;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.UI;

import java.util.List;

/**
 * Custom menu command to navigate to a specific state
 */
public class MenuCommand implements MenuBar.Command {

    private MenuBar mb;
    private String address;
    private View view;
    private Class clazz;
    private boolean viewCached;

    /**
     * Constructor - lazy
     * Will create a lazy (class-based) view provider
     * The view will be instantiated by the view provider from the provided class
     * The viewCached parameter controls if the view will be instantiated only once
     * or each time is requested bu yhe Navigator.
     *
     * @param mb         the MenuBar
     * @param address    the address for the Navigator
     * @param clazz      the class to instantiate (must implement View)
     * @param viewCached true to instantiated only once, false to instantiate each time
     */
    public MenuCommand(MenuBar mb, String address, Class clazz, boolean viewCached) {
        this.mb = mb;
        this.address = address;
        this.clazz = clazz;
        this.viewCached = viewCached;
    }

    /**
     * Constructor - lazy, cached
     */
    public MenuCommand(MenuBar mb, String address, Class clazz) {
        this(mb, address, clazz, true);
    }// end of constructor

    /**
     * Constructor - heavyweight
     * Will create a heavyweight view provider
     * The view provided here will be used
     *
     * @param mb      the MenuBar
     * @param address the address for the Navigator
     * @param view    the view to diplay
     */
    public MenuCommand(MenuBar mb, String address, View view) {
        this.mb = mb;
        this.address = address;
        this.view = view;
    }


    /**
     * The item has been selected.
     * Navigate to the View and select the item in the menubar
     */
    public void menuSelected(MenuBar.MenuItem selectedItem) {
        // Navigate to a specific state
        // ui.getNavigator().navigateTo(address);
        UI.getCurrent().getNavigator().navigateTo(address);

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

    public Class getClazz() {
        return clazz;
    }

    public boolean isViewCached() {
        return viewCached;
    }

    public View getView() {
        return view;
    }
}// end of class
